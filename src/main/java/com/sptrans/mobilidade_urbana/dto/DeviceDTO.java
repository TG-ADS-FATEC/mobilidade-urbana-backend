package com.sptrans.mobilidade_urbana.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.entities.Platform;

public class DeviceDTO {
	
	private UUID deviceToken;
	private Platform platform;
	private String appVersion;
	private LocalDateTime createdAt;
	
	public DeviceDTO() {}

	public DeviceDTO(UUID deviceToken, Platform platform, String appVersion, LocalDateTime createdAt) {
		super();
		this.deviceToken = deviceToken;
		this.platform = platform;
		this.appVersion = appVersion;
		this.createdAt = createdAt;
	}
	
	public DeviceDTO(Device entity) {
		deviceToken = entity.getDeviceToken();
		platform = entity.getPlatform();
		appVersion = entity.getAppVersion();
		createdAt = entity.getCreatedAt();
	}

	public UUID getDeviceToken() {
		return deviceToken;
	}

	public Platform getPlatform() {
		return platform;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
}
