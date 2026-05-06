package com.sptrans.mobilidade_urbana.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sptrans.mobilidade_urbana.dto.PreferenceDTO;
import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.entities.Preference;
import com.sptrans.mobilidade_urbana.entities.Profile;
import com.sptrans.mobilidade_urbana.repositories.PreferenceRepository;
import com.sptrans.mobilidade_urbana.repositories.ProfileRepository;
import com.sptrans.mobilidade_urbana.services.exceptions.DatabaseException;
import com.sptrans.mobilidade_urbana.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PreferenceService {
	
	@Autowired
	private PreferenceRepository repository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Transactional(readOnly = true)
	public PreferenceDTO findById(UUID preferenceId) {
		Preference preference = repository.findById(preferenceId).orElseThrow(
				() -> new ResourceNotFoundException("Preferência não encontrada"));
		return new PreferenceDTO(preference);
	}
	
	
	@Transactional
	public PreferenceDTO insert(Device device, PreferenceDTO dto) {
		Preference preference = new Preference();
		copyDtoToEntity(dto, preference);
		
		preference = repository.save(preference);
		
		Profile profile = profileRepository.findByDevice_DeviceId(device.getDeviceId())
				.orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));
		
		profile.setPreference(preference);
		
		profileRepository.save(profile);
		
		return new PreferenceDTO(preference);
	}
	
	@Transactional
	public PreferenceDTO update(UUID preferenceId, PreferenceDTO dto) {
		try {
			Preference entity = repository.getReferenceById(preferenceId);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new PreferenceDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Preferência não encontrada");
		}
	}
	
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(UUID preferenceId) {
		if(!repository.existsById(preferenceId)) {
			throw new ResourceNotFoundException("Preferência inexistente");
		}
		try {
			repository.deleteById(preferenceId);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
	}
	
	@Transactional
	public PreferenceDTO findByDevice(Device device) {
		 Preference preference = repository.findByDeviceId(device.getDeviceId())
				.orElseThrow(() -> new RuntimeException("Preferência não encontrada"));
		 
		 return new PreferenceDTO(preference);
	}
	
	@Transactional
	public PreferenceDTO update(Device device, PreferenceDTO dto) {
		Preference preference = repository.findByDeviceId(device.getDeviceId())
		.orElseThrow(() -> new RuntimeException("Preferência não encontrada"));
		
		copyDtoToEntity(dto, preference);
		
		preference = repository.save(preference);
		
		return new PreferenceDTO(preference);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Device device) {
		Preference preference = repository.findByDeviceId(device.getDeviceId())
				.orElseThrow(() -> new ResourceNotFoundException("Preferência inexistente"));
		try {
			repository.delete(preference);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
		
	}
	
	
	private void copyDtoToEntity(PreferenceDTO dto, Preference entity) {
		entity.setTransportTypes(dto.getTransportTypes());
		entity.setRoutePreference(dto.getRoutePreference());
		entity.setSlowPace(dto.getSlowPace());
		entity.setMaxWalkingTime(dto.getMaxWalkingTime());
	}

}
