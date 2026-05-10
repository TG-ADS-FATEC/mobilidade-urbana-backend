package com.sptrans.mobilidade_urbana.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sptrans.mobilidade_urbana.dto.FavoriteDTO;
import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.entities.Favorite;
import com.sptrans.mobilidade_urbana.entities.Profile;
import com.sptrans.mobilidade_urbana.repositories.FavoriteRepository;
import com.sptrans.mobilidade_urbana.repositories.ProfileRepository;
import com.sptrans.mobilidade_urbana.services.exceptions.DatabaseException;
import com.sptrans.mobilidade_urbana.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FavoriteService {
	
	@Autowired
	private FavoriteRepository repository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Transactional(readOnly = true)
	public FavoriteDTO findById(Long favoriteId) {
		Favorite favorite = repository.findById(favoriteId).orElseThrow(
				() -> new ResourceNotFoundException("Favorito não encontrado"));
		return new FavoriteDTO(favorite);
	}
	
	@Transactional(readOnly=true)
	public List<FavoriteDTO> findByDevice(Device device){
		
		Profile profile = profileRepository.findByDevice_DeviceId(device.getDeviceId())
				.orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));
		
		List<Favorite> favorites = repository.findByProfile(profile);
		
		return favorites.stream().map(FavoriteDTO::new).toList();
	}
	
	@Transactional
	public FavoriteDTO insert(Device device, FavoriteDTO dto) {
		
		Profile profile = profileRepository.findByDevice_DeviceId(device.getDeviceId())
				.orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));
		
		Favorite entity = new Favorite();
		copyDtoToEntity(dto, entity);
		
		entity.setProfile(profile);
		
		profile.getFavorites().add(entity);
		
		entity = repository.save(entity);
		
		return new FavoriteDTO(entity);
	}
	
	@Transactional
	public FavoriteDTO update(Device device, Long favoriteId, FavoriteDTO dto) {
		try {
			Profile profile = profileRepository.findByDevice_DeviceId(device.getDeviceId())
					.orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));
			Favorite entity = repository.findById(favoriteId)
					.orElseThrow(() -> new ResourceNotFoundException("Favorito não encontrado"));
			
			if(!entity.getProfile().getProfileId().equals(profile.getProfileId())) {
				throw new ResourceNotFoundException("Favorito não pertence ao perfil");
			}
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new FavoriteDTO(entity);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Favorito não encontrado");
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Device device, Long favoriteId) {
		Profile profile = profileRepository.findByDevice_DeviceId(device.getDeviceId())
				.orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));
		Favorite entity = repository.findById(favoriteId)
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
	
	private void copyDtoToEntity(FavoriteDTO dto, Favorite entity) {
		entity.setFavoriteName(dto.getFavoriteName());
	}
	

}
