package com.sptrans.mobilidade_urbana.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.sptrans.mobilidade_urbana.entities.Profile;

import jakarta.validation.constraints.Email;

public class ProfileDTO {
	
	private Long profileId;
	@Email(message = "Email inválido")
	private String email;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private UUID deviceToken;
	private Long preferenceId;
	
	public ProfileDTO() {}

	public ProfileDTO(Long profileId, String email, LocalDateTime createdAt, LocalDateTime updatedAt, UUID deviceToken,
			Long preferenceId) {
		super();
		this.profileId = profileId;
		this.email = email;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deviceToken = deviceToken;
		this.preferenceId = preferenceId;
	}
	
	public ProfileDTO(Profile entity) {
		profileId = entity.getProfileId();
		email = entity.getEmail();
		createdAt = entity.getCreatedAt();
		updatedAt = entity.getUpdatedAt();
		deviceToken = entity.getDevice().getDeviceToken();
		preferenceId = entity.getPreference().getPreferenceId();
	}

	public Long getprofileId() {
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

	public UUID getDeviceToken() {
		return deviceToken;
	}

	public Long getPreferenceId() {
		return preferenceId;
	}
	

}
