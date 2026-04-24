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
import com.sptrans.mobilidade_urbana.repositories.DeviceRepository;
import com.sptrans.mobilidade_urbana.repositories.PreferenceRepository;
import com.sptrans.mobilidade_urbana.services.exceptions.DatabaseException;
import com.sptrans.mobilidade_urbana.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PreferenceService {
	
	@Autowired
	private PreferenceRepository repository;
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	@Transactional(readOnly = true)
	public PreferenceDTO findById(Long preferenceId) {
		Preference preference = repository.findById(preferenceId).orElseThrow(
				() -> new ResourceNotFoundException("Preferência não encontrada"));
		return new PreferenceDTO(preference);
	}
	
	@Transactional(readOnly = true)
	public PreferenceDTO findByDeviceToken(UUID deviceToken) {
		Preference preference = repository.findByDevice_DeviceToken(deviceToken).orElseThrow(
				() -> new ResourceNotFoundException("Preferência não encontrada"));
		return new PreferenceDTO(preference);
	}
	
	@Transactional
	public PreferenceDTO insert(PreferenceDTO dto) {
		Preference entity = new Preference();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new PreferenceDTO(entity);
	}
	
	@Transactional
	public PreferenceDTO update(Long preferenceId, PreferenceDTO dto) {
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
	
	@Transactional
	public PreferenceDTO update(UUID deviceToken, PreferenceDTO dto) {
		try {
			Preference entity = repository.findByDevice_DeviceToken(deviceToken).
					orElseThrow(() -> new ResourceNotFoundException("Preferência não encontrada"));
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new PreferenceDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Preferência não encontrada");
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long preferenceId) {
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
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(UUID deviceToken) {
		Preference entity = repository.findByDevice_DeviceToken(deviceToken).
				orElseThrow(() -> new ResourceNotFoundException("Preferência não encontrada"));
		try {
			repository.delete(entity);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
	}
	
	private void copyDtoToEntity(PreferenceDTO dto, Preference entity) {
		entity.setTransportTypes(dto.getTransportTypes());
		entity.setRoutePreference(dto.getRoutePreference());
		entity.setSlowPace(dto.getSlowPace());
		entity.setMaxWalkingTime(dto.getMaxWalkingTime());
		Device device = deviceRepository.findById(dto.getDeviceToken())
		        .orElseThrow(() -> new ResourceNotFoundException("Dispositivo não encontrado"));
		entity.setDevice(device);
	}

}
