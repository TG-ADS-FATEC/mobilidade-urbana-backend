package com.sptrans.mobilidade_urbana.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sptrans.mobilidade_urbana.dto.UserDTO;
import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.entities.Preference;
import com.sptrans.mobilidade_urbana.entities.User;
import com.sptrans.mobilidade_urbana.repositories.DeviceRepository;
import com.sptrans.mobilidade_urbana.repositories.PreferenceRepository;
import com.sptrans.mobilidade_urbana.repositories.UserRepository;
import com.sptrans.mobilidade_urbana.services.exceptions.DatabaseException;
import com.sptrans.mobilidade_urbana.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	@Autowired
	private PreferenceRepository preferenceRepository;
	
	@Transactional(readOnly = true)
	public UserDTO findById(Long userId) {
		User user = repository.findById(userId).orElseThrow(
				() -> new ResourceNotFoundException("Usuário não encontrado"));
		return new UserDTO(user);
	}
	
	@Transactional
	public UserDTO insert(UserDTO dto) {
		User entity = new User();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new UserDTO(entity);
	}
	
	@Transactional
	public UserDTO update(Long userId, UserDTO dto) {
		try {
			User entity = repository.getReferenceById(userId);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new UserDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Usuário não encontrado");
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long userId) {
		if(!repository.existsById(userId)) {
			throw new ResourceNotFoundException("Usuário inexistente");
		}
		try {
			repository.deleteById(userId);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
	}
	
	private void copyDtoToEntity(UserDTO dto, User entity) {
		entity.setEmail(dto.getEmail());
		Device device = deviceRepository.findById(dto.getDeviceId())
		        .orElseThrow(() -> new ResourceNotFoundException("Dispositivo não encontrado"));
		entity.setDevice(device);
		Preference preference = preferenceRepository.findById(dto.getPreferenceId())
		        .orElseThrow(() -> new ResourceNotFoundException("Preferência não encontrada"));
		entity.setPreference(preference);
	}

}
