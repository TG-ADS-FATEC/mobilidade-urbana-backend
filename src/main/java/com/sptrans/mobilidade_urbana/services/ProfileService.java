package com.sptrans.mobilidade_urbana.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sptrans.mobilidade_urbana.dto.ProfileDTO;
import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.entities.Profile;
import com.sptrans.mobilidade_urbana.mappers.ProfileMapper;
import com.sptrans.mobilidade_urbana.repositories.ProfileRepository;
import com.sptrans.mobilidade_urbana.services.exceptions.DatabaseException;
import com.sptrans.mobilidade_urbana.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfileService {
	
	@Autowired
	private ProfileRepository repository;
	
	@Autowired
	private ProfileMapper mapper;
	
	@Transactional(readOnly = true)
	public ProfileDTO findByDeviceId(UUID deviceId) {
		Profile profile = repository.findByDevice_DeviceId(deviceId).orElseThrow(
				() -> new ResourceNotFoundException("Perfil não encontrado"));
		return mapper.toDTO(profile);
	}
	
	@Transactional
	public ProfileDTO insert(Device device, ProfileDTO dto) {
		Profile entity = new Profile();
		
		entity = mapper.toEntity(dto, device.getDeviceId());
		
		entity = repository.save(entity);
		
		return mapper.toDTO(entity);
	}
	
	@Transactional
	public ProfileDTO updateByDeviceId(UUID deviceId, ProfileDTO dto) {
		try {
			Profile entity = repository.findByDevice_DeviceId(deviceId).
					orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));
			
			entity.setEmail(dto.getEmail());
			entity = repository.save(entity);
			return mapper.toDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Perfil não encontrado");
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void deleteByDeviceId(UUID deviceId) {
		Profile entity = repository.findByDevice_DeviceId(deviceId).
				orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));
		try {
			repository.delete(entity);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
	}

}
