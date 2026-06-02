package com.sptrans.mobilidade_urbana.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sptrans.mobilidade_urbana.dto.FavoriteTripDTO;
import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.entities.FavoriteTrip;
import com.sptrans.mobilidade_urbana.entities.Profile;
import com.sptrans.mobilidade_urbana.mappers.FavoriteTripMapper;
import com.sptrans.mobilidade_urbana.repositories.FavoriteTripRepository;
import com.sptrans.mobilidade_urbana.repositories.ProfileRepository;
import com.sptrans.mobilidade_urbana.services.exceptions.DatabaseException;
import com.sptrans.mobilidade_urbana.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FavoriteTripService {
	
	@Autowired
	private FavoriteTripRepository repository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private FavoriteTripMapper mapper;
	
	@Transactional(readOnly = true)
	public FavoriteTripDTO findById(Long favoriteTripId) {
		FavoriteTrip favoriteTrip = repository.findById(favoriteTripId).orElseThrow(
				() -> new ResourceNotFoundException("Favorito não encontrado"));
		return mapper.toDTO(favoriteTrip);
	}
	
	@Transactional(readOnly=true)
	public List<FavoriteTripDTO> findByDevice(Device device){
		
		Profile profile = profileRepository.findByDevice_DeviceId(device.getDeviceId())
				.orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));
		
		List<FavoriteTrip> favorites = repository.findByProfile(profile);
		
		return mapper.toDTOList(favorites);
	}
	
	@Transactional
	public FavoriteTripDTO insert(Device device, FavoriteTripDTO dto) {
		
		Profile profile = profileRepository.findByDevice_DeviceId(device.getDeviceId())
				.orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));
		
		FavoriteTrip entity = mapper.toEntity(dto, profile.getProfileId());
		
		profile.getFavoriteTrips().add(entity);
		
		entity = repository.save(entity);
		
		return mapper.toDTO(entity);
	}
	
	@Transactional
	public FavoriteTripDTO update(Long favoriteTripId, FavoriteTripDTO dto) {
		try {
			FavoriteTrip entity = repository.getReferenceById(favoriteTripId);
			
			entity.setFavoriteTripName(dto.getFavoriteTripName());
			entity.setDestinationLatitude(dto.getDestinationLatitude());
			entity.setDestinationLongitude(dto.getDestinationLongitude());
			
			return mapper.toDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Perfil não encontrado");
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Device device, Long favoriteTripId) {
		Profile profile = profileRepository.findByDevice_DeviceId(device.getDeviceId())
				.orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));
		FavoriteTrip entity = repository.findById(favoriteTripId)
				.orElseThrow(() -> new ResourceNotFoundException("Favorito não encontrado"));
		
		if(!entity.getProfile().getProfileId().equals(profile.getProfileId())) {
			throw new ResourceNotFoundException("Favorito não pertence ao perfil");
		}
		try {
			repository.delete(entity);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
		
	}

}
