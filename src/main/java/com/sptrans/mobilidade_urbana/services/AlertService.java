package com.sptrans.mobilidade_urbana.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sptrans.mobilidade_urbana.dto.AlertDTO;
import com.sptrans.mobilidade_urbana.entities.Alert;
import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.entities.Profile;
import com.sptrans.mobilidade_urbana.repositories.AlertRepository;
import com.sptrans.mobilidade_urbana.repositories.ProfileRepository;
import com.sptrans.mobilidade_urbana.services.exceptions.DatabaseException;
import com.sptrans.mobilidade_urbana.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AlertService {
	
	@Autowired
	private AlertRepository repository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Transactional(readOnly = true)
	public AlertDTO findById(Long alertId) {
		Alert alert = repository.findById(alertId).orElseThrow(
				() -> new ResourceNotFoundException("Alerta não encontrado"));
		return new AlertDTO(alert);
	}
	
	@Transactional(readOnly=true)
	public List<AlertDTO> findByDevice(Device device){
		
		Profile profile = profileRepository.findByDevice_DeviceId(device.getDeviceId())
				.orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));
		
		List<Alert> alerts = repository.findByProfile(profile);
		
		return alerts.stream().map(AlertDTO::new).toList();
	}
	
	@Transactional
	public AlertDTO insert(Device device, AlertDTO dto) {
		
		Profile profile = profileRepository.findByDevice_DeviceId(device.getDeviceId())
				.orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));
		
		Alert entity = new Alert();
		copyDtoToEntity(dto, entity);
		
		entity.setProfile(profile);
		
		profile.getAlerts().add(entity);
		
		entity = repository.save(entity);
		
		return new AlertDTO(entity);
	}
	
	@Transactional
	public AlertDTO update(Device device, Long alertId, AlertDTO dto) {
		try {
			Profile profile = profileRepository.findByDevice_DeviceId(device.getDeviceId())
					.orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));
			Alert entity = repository.findById(alertId)
					.orElseThrow(() -> new ResourceNotFoundException("Alerta não encontrado"));
			
			if(!entity.getProfile().getProfileId().equals(profile.getProfileId())) {
				throw new ResourceNotFoundException("Alerta não pertence ao perfil");
			}
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new AlertDTO(entity);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Alerta não encontrado");
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Device device, Long alertId) {
		Profile profile = profileRepository.findByDevice_DeviceId(device.getDeviceId())
				.orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));
		Alert entity = repository.findById(alertId)
				.orElseThrow(() -> new ResourceNotFoundException("Alerta não encontrado"));
		
		if(!entity.getProfile().getProfileId().equals(profile.getProfileId())) {
			throw new ResourceNotFoundException("Alerta não pertence ao perfil");
		}
		try {
			repository.delete(entity);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
		
	}
	
	private void copyDtoToEntity(AlertDTO dto, Alert entity) {
		entity.setMinutesBefore(dto.getMinutesBefore());
		entity.setActive(dto.getActive());
	}

}
