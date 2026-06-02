package com.sptrans.mobilidade_urbana.mappers;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.sptrans.mobilidade_urbana.dto.ProfileDTO;
import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.entities.Preference;
import com.sptrans.mobilidade_urbana.entities.Profile;

import jakarta.persistence.EntityManager;

@Component
public class ProfileMapper {
	
	private final EntityManager entityManager;

	public ProfileMapper(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Profile toEntity(ProfileDTO dto, UUID deviceId, UUID profileId) {
		
		Profile profile = new Profile();
		
		profile.setEmail(dto.getEmail());
		profile.setCreatedAt(dto.getCreatedAt());
		profile.setUpdatedAt(dto.getUpdatedAt());
		profile.setDevice(entityManager.getReference(Device.class, deviceId));
		profile.setPreference(entityManager.getReference(Preference.class, profileId));
		
		return profile;
	}
	
	public ProfileDTO toDTO(Profile entity) {
		if(entity==null) return null;
		
		return new ProfileDTO(
				entity.getEmail(),
				entity.getCreatedAt(),
				entity.getUpdatedAt());
	}

}
