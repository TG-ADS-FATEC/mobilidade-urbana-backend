package com.sptrans.mobilidade_urbana.dto;

import java.util.UUID;

import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.entities.Platform;

public class DeviceDTO {
	
	private UUID deviceId;
	private UUID deviceToken;
	private Platform platform;
	private String appVersion;
	
	public DeviceDTO() {}

	public DeviceDTO(UUID deviceId, UUID deviceToken, Platform platform, String appVersion) {
		super();
		this.deviceId = deviceId;
		this.deviceToken = deviceToken;
		this.platform = platform;
		this.appVersion = appVersion;
	}


	public DeviceDTO(Device entity) {
		deviceId = entity.getDeviceId();
		deviceToken = entity.getDeviceToken();
		platform = entity.getPlatform();
		appVersion = entity.getAppVersion();
	}

	public UUID getDeviceId() {
		return deviceId;
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
	
}
