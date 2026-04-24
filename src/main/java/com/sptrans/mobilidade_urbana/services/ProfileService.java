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
	public ProfileDTO findById(Long profileId) {
		Profile profile = repository.findById(profileId).orElseThrow(
				() -> new ResourceNotFoundException("Usuário não encontrado"));
		return new ProfileDTO(profile);
	}
	
	@Transactional(readOnly = true)
	public ProfileDTO findByDeviceToken(UUID deviceToken) {
		Profile profile = repository.findByDevice_DeviceToken(deviceToken).orElseThrow(
				() -> new ResourceNotFoundException("Usuário não encontrado"));
		return new ProfileDTO(profile);
	}
	
	@Transactional
	public ProfileDTO insert(ProfileDTO dto) {
		Profile entity = new Profile();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ProfileDTO(entity);
	}
	
	@Transactional
	public ProfileDTO update(Long profileId, ProfileDTO dto) {
		try {
			Profile entity = repository.getReferenceById(profileId);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new ProfileDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Usuário não encontrado");
		}
	}
	
	@Transactional
	public ProfileDTO update(UUID deviceToken, ProfileDTO dto) {
		try {
			Profile entity = repository.findByDevice_DeviceToken(deviceToken).
					orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new ProfileDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Usuário não encontrada");
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long profileId) {
		if(!repository.existsById(profileId)) {
			throw new ResourceNotFoundException("Usuário inexistente");
		}
		try {
			repository.deleteById(profileId);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(UUID deviceToken) {
		Profile entity = repository.findByDevice_DeviceToken(deviceToken).
				orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
		try {
			repository.delete(entity);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
	}
	
	private void copyDtoToEntity(ProfileDTO dto, Profile entity) {
		entity.setEmail(dto.getEmail());
		Device device = deviceRepository.findById(dto.getDeviceToken())
		        .orElseThrow(() -> new ResourceNotFoundException("Dispositivo não encontrado"));
		entity.setDevice(device);
		Preference preference = preferenceRepository.findById(dto.getPreferenceId())
		        .orElseThrow(() -> new ResourceNotFoundException("Preferência não encontrada"));
		entity.setPreference(preference);
	}

}
