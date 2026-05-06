package com.sptrans.mobilidade_urbana.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sptrans.mobilidade_urbana.dto.ProfileDTO;
import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.entities.Preference;
import com.sptrans.mobilidade_urbana.entities.Profile;
import com.sptrans.mobilidade_urbana.repositories.DeviceRepository;
import com.sptrans.mobilidade_urbana.repositories.PreferenceRepository;
import com.sptrans.mobilidade_urbana.repositories.ProfileRepository;
import com.sptrans.mobilidade_urbana.services.exceptions.DatabaseException;
import com.sptrans.mobilidade_urbana.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfileService {
	
	@Autowired
	private ProfileRepository repository;
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	@Autowired
	private PreferenceRepository preferenceRepository;
	
	@Transactional(readOnly = true)
	public ProfileDTO findById(UUID profileId) {
		Profile profile = repository.findById(profileId).orElseThrow(
				() -> new ResourceNotFoundException("Perfil não encontrado"));
		return new ProfileDTO(profile);
	}
	
	@Transactional(readOnly = true)
	public ProfileDTO findByDeviceId(UUID deviceId) {
		Profile profile = repository.findByDevice_DeviceId(deviceId).orElseThrow(
				() -> new ResourceNotFoundException("Perfil não encontrado"));
		return new ProfileDTO(profile);
	}
	
	@Transactional
	public ProfileDTO insert(Device device, ProfileDTO dto) {
		Profile entity = new Profile();
		
		entity.setEmail(dto.getEmail());
		entity.setDevice(device);
		if(dto.getPreferenceId() != null) {
		Preference preference = preferenceRepository.findById(dto.getPreferenceId())
		        .orElseThrow(() -> new ResourceNotFoundException("Preferência não encontrada"));
		entity.setPreference(preference);
		}
		entity = repository.save(entity);
		return new ProfileDTO(entity);
	}
	
	@Transactional
	public ProfileDTO update(UUID profileId, ProfileDTO dto) {
		try {
			Profile entity = repository.getReferenceById(profileId);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new ProfileDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Perfil não encontrado");
		}
	}
	
	@Transactional
	public ProfileDTO updateByDeviceId(UUID deviceId, ProfileDTO dto) {
		try {
			Profile entity = repository.findByDevice_DeviceId(deviceId).
					orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new ProfileDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Perfil não encontrado");
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(UUID profileId) {
		if(!repository.existsById(profileId)) {
			throw new ResourceNotFoundException("Perfil inexistente");
		}
		try {
			repository.deleteById(profileId);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void deleteWithDeviceId(UUID deviceId) {
		Profile entity = repository.findByDevice_DeviceId(deviceId).
				orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));
		try {
			repository.delete(entity);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
	}
	
	@Transactional
	public void deleteByDevice(Device device) {
		repository.findByDevice_DeviceId(device.getDeviceId()).
				orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));
		try {
			repository.deleteByDeviceId(device.getDeviceId());
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
	}
	
	
	
	private void copyDtoToEntity(ProfileDTO dto, Profile entity) {
		entity.setEmail(dto.getEmail());
		entity.setDevice(entity.getDevice());
		if(dto.getPreferenceId() != null) {
		Preference preference = preferenceRepository.findById(dto.getPreferenceId())
		        .orElseThrow(() -> new ResourceNotFoundException("Preferência não encontrada"));
		entity.setPreference(preference);
		}
	}
	
	public Profile getProfileFromDevice(Device device) {
		return repository.findByDevice_DeviceId(device.getDeviceId())
				.orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
	}

}
