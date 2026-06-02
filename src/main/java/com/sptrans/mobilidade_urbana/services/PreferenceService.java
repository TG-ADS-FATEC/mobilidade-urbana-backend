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
import com.sptrans.mobilidade_urbana.mappers.PreferenceMapper;
import com.sptrans.mobilidade_urbana.repositories.PreferenceRepository;
import com.sptrans.mobilidade_urbana.services.exceptions.DatabaseException;
import com.sptrans.mobilidade_urbana.services.exceptions.ResourceNotFoundException;

@Service
public class PreferenceService {
	
	@Autowired
	private PreferenceRepository repository;
	
	@Autowired
	private PreferenceMapper mapper;
	
	@Transactional
	public PreferenceDTO findByDeviceId(UUID deviceId) {
		 Preference preference = repository.findByDevice_DeviceId(deviceId)
				.orElseThrow(() -> new ResourceNotFoundException("Preferência não encontrada"));
		 
		 return mapper.toDTO(preference);
	}
	
	@Transactional
	public PreferenceDTO insert(Device device, PreferenceDTO dto) {
		Preference preference = new Preference();
		
		preference = mapper.toEntity(dto, device.getDeviceId());
		
		preference = repository.save(preference);
		
		return mapper.toDTO(preference);
	}
	
	@Transactional
	public PreferenceDTO update(Device device, PreferenceDTO dto) {
		Preference entity = repository.findByDevice_DeviceId(device.getDeviceId())
		.orElseThrow(() -> new ResourceNotFoundException("Preferência não encontrada"));
		
		entity.setTransportTypes(dto.getTransportTypes());
		entity.setRoutePreference(dto.getRoutePreference());
		entity.setSlowPace(dto.getSlowPace());
		entity.setMaxWalkingTime(dto.getMaxWalkingTime());
		
		entity = repository.save(entity);
		
		return mapper.toDTO(entity);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Device device) {
		Preference preference = repository.findByDevice_DeviceId(device.getDeviceId())
				.orElseThrow(() -> new ResourceNotFoundException("Preferência inexistente"));
		try {
			repository.delete(preference);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
		
	}

}
