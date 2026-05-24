package com.sptrans.mobilidade_urbana.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name="devices")
public class Device {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(updatable = false, nullable=false)
	private UUID deviceId;
	@Column(unique = true, nullable=false)
	private UUID deviceToken;
	private Platform platform;
	private String appVersion;
	@Column(nullable = false)
	private Boolean active = true;
	@Column(nullable = false)
	private Integer tokenVersion = 0;
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private LocalDateTime createdAt;
	
	@OneToOne(mappedBy="device")
	private Profile profile; 
	
	public Device() {}
	
	public Device(UUID deviceId, UUID deviceToken, Platform platform, String appVersion) {
		super();
		this.deviceId = deviceId;
		this.deviceToken = deviceToken;
		this.platform = platform;
		this.appVersion = appVersion;
	}

	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
	}

	public UUID getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(UUID deviceId) {
		this.deviceId = deviceId;
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getTokenVersion() {
		return tokenVersion;
	}

	public void setTokenVersion(Integer tokenVersion) {
		this.tokenVersion = tokenVersion;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
		

}
