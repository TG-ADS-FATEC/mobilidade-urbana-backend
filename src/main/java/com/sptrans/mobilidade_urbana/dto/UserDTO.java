package com.sptrans.mobilidade_urbana.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.sptrans.mobilidade_urbana.entities.User;

import jakarta.validation.constraints.Email;

public class UserDTO {
	
	private Long userId;
	@Email(message = "Email inválido")
	private String email;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private UUID deviceId;
	private Long preferenceId;
	
	public UserDTO() {}

	public UserDTO(Long userId, String email, LocalDateTime createdAt, LocalDateTime updatedAt, UUID deviceId,
			Long preferenceId) {
		super();
		this.userId = userId;
		this.email = email;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deviceId = deviceId;
		this.preferenceId = preferenceId;
	}
	
	public UserDTO(User entity) {
		userId = entity.getUserId();
		email = entity.getEmail();
		createdAt = entity.getCreatedAt();
		updatedAt = entity.getUpdatedAt();
		deviceId = entity.getDevice().getDeviceToken();
		preferenceId = entity.getPreference().getPreferenceId();
	}

	public Long getUserId() {
		return userId;
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

	public Long getPreferenceId() {
		return preferenceId;
	}
	

}
