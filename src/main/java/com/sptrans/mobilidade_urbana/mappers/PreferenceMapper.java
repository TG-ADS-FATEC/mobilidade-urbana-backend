package com.sptrans.mobilidade_urbana.mappers;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.sptrans.mobilidade_urbana.dto.PreferenceDTO;
import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.entities.Preference;

import jakarta.persistence.EntityManager;

@Component
public class PreferenceMapper {
	
	private final EntityManager entityManager;

	public PreferenceMapper(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Preference toEntity(PreferenceDTO dto, UUID deviceId) {
		
		Preference preference = new Preference();
		
		preference.setTransportTypes(dto.getTransportTypes());
		preference.setRoutePreference(dto.getRoutePreference());
		preference.setSlowPace(dto.getSlowPace());
		preference.setMaxWalkingTime(dto.getMaxWalkingTime());
		preference.setCreatedAt(dto.getCreatedAt());
		preference.setUpdatedAt(dto.getUpdatedAt());
		preference.setDevice(entityManager.getReference(Device.class, deviceId));
		
		return preference;
	}
	
	public PreferenceDTO toDTO(Preference entity) {
		
		if(entity==null) return null;
		
		return new PreferenceDTO(
				entity.getTransportTypes(),
				entity.getRoutePreference(),
				entity.getSlowPace(),
				entity.getMaxWalkingTime(),
				entity.getCreatedAt(),
				entity.getUpdatedAt());
	}

}
