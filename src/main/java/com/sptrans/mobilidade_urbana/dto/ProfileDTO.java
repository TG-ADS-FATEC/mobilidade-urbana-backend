package com.sptrans.mobilidade_urbana.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.sptrans.mobilidade_urbana.entities.Profile;

import jakarta.validation.constraints.Email;

public class ProfileDTO {
	
	private UUID profileId;
	@Email(message = "Email inválido")
	private String email;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private UUID deviceId;
	private UUID preferenceId;
	
	public ProfileDTO() {}

	public ProfileDTO(UUID profileId, String email, LocalDateTime createdAt, LocalDateTime updatedAt, UUID deviceId,
			UUID preferenceId) {
		super();
		this.profileId = profileId;
		this.email = email;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deviceId = deviceId;
		this.preferenceId = preferenceId;
	}
	
	public ProfileDTO(Profile entity) {
		profileId = entity.getProfileId();
		email = entity.getEmail();
		createdAt = entity.getCreatedAt();
		updatedAt = entity.getUpdatedAt();
		deviceId = entity.getDevice().getDeviceId();
		if(entity.getPreference()!=null) {
			preferenceId = entity.getPreference().getPreferenceId();
		}
		else {
			preferenceId = null;
		}
		
	}

	public UUID getprofileId() {
		return profileId;
	}

	public String getEmail() {
		return email;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public UUID getDeviceId() {
		return deviceId;
	}

	public UUID getPreferenceId() {
		return preferenceId;
	}
	

}
