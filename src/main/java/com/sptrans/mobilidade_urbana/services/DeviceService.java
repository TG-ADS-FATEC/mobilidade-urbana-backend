package com.sptrans.mobilidade_urbana.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sptrans.mobilidade_urbana.dto.DeviceDTO;
import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.repositories.DeviceRepository;
import com.sptrans.mobilidade_urbana.services.exceptions.DatabaseException;
import com.sptrans.mobilidade_urbana.services.exceptions.ResourceNotFoundException;

@Service
public class DeviceService {
	
	@Autowired
	private DeviceRepository repository;
	
	@Transactional(readOnly = true)
	public DeviceDTO findById(UUID deviceToken) {
		Device device = repository.findById(deviceToken).orElseThrow(
				() -> new ResourceNotFoundException("Dispositivo não encontrado"));
		return new DeviceDTO(device);
	}
	
	
	@Transactional
	public DeviceDTO insert(DeviceDTO dto) {
		Device entity = new Device();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new DeviceDTO(entity);
	}
	
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(UUID deviceToken) {
		if(!repository.existsById(deviceToken)) {
			throw new ResourceNotFoundException("Dispositivo inexistente");
		}
		try {
			repository.deleteById(deviceToken);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
	}
	
	private void copyDtoToEntity(DeviceDTO dto, Device entity) {
		entity.setDeviceToken(dto.getDeviceToken());
		entity.setPlatform(dto.getPlatform());
		entity.setAppVersion(dto.getAppVersion());
	}

}
