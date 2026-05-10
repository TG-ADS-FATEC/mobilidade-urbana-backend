package com.sptrans.mobilidade_urbana.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sptrans.mobilidade_urbana.dto.NotificationDTO;
import com.sptrans.mobilidade_urbana.dto.NotificationRequestDTO;
import com.sptrans.mobilidade_urbana.entities.Alert;
import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.entities.Notification;
import com.sptrans.mobilidade_urbana.entities.NotificationStatus;
import com.sptrans.mobilidade_urbana.entities.Profile;
import com.sptrans.mobilidade_urbana.repositories.AlertRepository;
import com.sptrans.mobilidade_urbana.repositories.NotificationRepository;
import com.sptrans.mobilidade_urbana.repositories.ProfileRepository;
import com.sptrans.mobilidade_urbana.services.exceptions.DatabaseException;
import com.sptrans.mobilidade_urbana.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class NotificationService {
	
	@Autowired
	private NotificationRepository repository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private AlertRepository alertRepository;
	
	@Transactional(readOnly = true)
	public NotificationDTO findById(Long notificationId) {
		Notification notification = repository.findById(notificationId).orElseThrow(
				() -> new ResourceNotFoundException("Notificação não encontrada"));
		return new NotificationDTO(notification);
	}
	
	@Transactional(readOnly=true)
	public List<NotificationDTO> findByDevice(Device device){
		
		Profile profile = profileRepository.findByDevice_DeviceId(device.getDeviceId())
				.orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));
		
		List<Notification> notifications = repository.findByProfile(profile);
		
		return notifications.stream().map(NotificationDTO::new).toList();
	}
	
	@Transactional
	public NotificationDTO insert(Device device, NotificationRequestDTO dto) {
		
		Profile profile = profileRepository.findByDevice_DeviceId(device.getDeviceId())
				.orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));
		
		Alert alert = alertRepository.findById(dto.getAlertId())
				.orElseThrow(() -> new ResourceNotFoundException("Alerta não encontrado"));
		
		//Implementação a ser feita futuramente quando tiver as entidades do GTFS com os dados. Sujeita a alteração.
		//Os dados virão do stopId presente no alertId (arrivalTime-minutesBefore)
		//LocalDateTime scheduledTime = alert.getArrivalTime().minusMinutes(alert.getMinutesBefore());
		
		Notification entity = new Notification();
		
		entity.setProfile(profile);
		entity.setAlert(alert);
		entity.setScheduledTime(dto.getScheduledTime());
		entity.setStatus(NotificationStatus.PENDING);
		entity.setSentAt(null);
		
		profile.getNotifications().add(entity);
		
		entity = repository.save(entity);
		
		return new NotificationDTO(entity);
	}
	
	@Transactional
	public NotificationDTO update(Device device, Long notificationId, NotificationDTO dto) {
		try {
			Profile profile = profileRepository.findByDevice_DeviceId(device.getDeviceId())
					.orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));
			Notification entity = repository.findById(notificationId)
					.orElseThrow(() -> new ResourceNotFoundException("Notificação não encontrada"));
			
			if(!entity.getProfile().getProfileId().equals(profile.getProfileId())) {
				throw new ResourceNotFoundException("Notificação não pertence ao perfil");
			}
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new NotificationDTO(entity);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Notificação não encontrada");
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Device device, Long notificationId) {
		Profile profile = profileRepository.findByDevice_DeviceId(device.getDeviceId())
				.orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));
		Notification entity = repository.findById(notificationId)
				.orElseThrow(() -> new ResourceNotFoundException("Notificação não encontrada"));
		
		if(!entity.getProfile().getProfileId().equals(profile.getProfileId())) {
			throw new ResourceNotFoundException("Notificação não pertence ao perfil");
		}
		try {
			repository.delete(entity);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
		
	}
	
	private void copyDtoToEntity(NotificationDTO dto, Notification entity){
		entity.setScheduledTime(dto.getScheduledTime());
	}

}
