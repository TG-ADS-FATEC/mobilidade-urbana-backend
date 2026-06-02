package com.sptrans.mobilidade_urbana.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sptrans.mobilidade_urbana.dto.FavoriteRouteDTO;
import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.entities.FavoriteRoute;
import com.sptrans.mobilidade_urbana.entities.Profile;
import com.sptrans.mobilidade_urbana.mappers.FavoriteRouteMapper;
import com.sptrans.mobilidade_urbana.repositories.FavoriteRouteRepository;
import com.sptrans.mobilidade_urbana.repositories.ProfileRepository;
import com.sptrans.mobilidade_urbana.services.exceptions.DatabaseException;
import com.sptrans.mobilidade_urbana.services.exceptions.ResourceNotFoundException;

@Service
public class FavoriteRouteService {
	
	@Autowired
	private FavoriteRouteRepository repository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private FavoriteRouteMapper mapper;
	
	@Transactional(readOnly = true)
	public FavoriteRouteDTO findById(Long favoriteRouteId) {
		FavoriteRoute favoriteRoute = repository.findById(favoriteRouteId).orElseThrow(
				() -> new ResourceNotFoundException("Favorito não encontrado"));
		return mapper.toDTO(favoriteRoute);
	}
	
	@Transactional(readOnly=true)
	public List<FavoriteRouteDTO> findByDevice(Device device){
		
		Profile profile = profileRepository.findByDevice_DeviceId(device.getDeviceId())
				.orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));
		
		List<FavoriteRoute> favorites = repository.findByProfile(profile);
		
		return mapper.toDTOList(favorites);
	}
	
	@Transactional
	public FavoriteRouteDTO insert(Device device, FavoriteRouteDTO dto) {
		
		Profile profile = profileRepository.findByDevice_DeviceId(device.getDeviceId())
				.orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));
		
		FavoriteRoute entity = mapper.toEntity(dto, profile.getProfileId());
		
		profile.getFavoriteRoutes().add(entity);
		
		entity = repository.save(entity);
		
		return mapper.toDTO(entity);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Device device, Long favoriteRouteId) {
		Profile profile = profileRepository.findByDevice_DeviceId(device.getDeviceId())
				.orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));
		FavoriteRoute entity = repository.findById(favoriteRouteId)
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
