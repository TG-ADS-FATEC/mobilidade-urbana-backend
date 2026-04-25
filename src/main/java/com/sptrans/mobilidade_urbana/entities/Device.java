package com.sptrans.mobilidade_urbana.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name="devices")
public class Device {
	
	@Id
	private UUID deviceToken;
	private Platform platform;
	private String appVersion;
	private LocalDateTime createdAt;
	
	@OneToOne(mappedBy="device", cascade=CascadeType.ALL)
	private Preference preference; 
	
	@OneToOne(mappedBy="device", cascade=CascadeType.ALL)
	private Profile profile; 
	
	public Device() {}

	public Device(UUID deviceToken, Platform platform, String appVersion, LocalDateTime createdAt) {
		super();
		this.deviceToken = deviceToken;
		this.platform = platform;
		this.appVersion = appVersion;
		this.createdAt = createdAt;
	}
	
	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
	}

	public UUID getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(UUID deviceToken) {
		this.deviceToken = deviceToken;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	

}
