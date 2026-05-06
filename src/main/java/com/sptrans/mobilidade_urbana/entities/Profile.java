package com.sptrans.mobilidade_urbana.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name="profiles")
public class Profile {
	
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	@Column(updatable = false, nullable=false)
	private UUID profileId;
	private String email;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	@OneToOne
	@JoinColumn(name="device_id", nullable=false, unique=true)
	private Device device;
	
	@OneToOne(cascade=CascadeType.ALL, orphanRemoval= true)
	@JoinColumn(name="preference_id", nullable=true, unique=true)
	private Preference preference;
	
	public Profile() {}

	

	public Profile(UUID profileId, String email, LocalDateTime createdAt, LocalDateTime updatedAt, Device device,
			Preference preference) {
		super();
		this.profileId = profileId;
		this.email = email;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.device = device;
		this.preference = preference;
	}
	
	@PrePersist
	public void prePersist() {
		LocalDateTime now = LocalDateTime.now();
		createdAt = now;
		updatedAt = now;
	}
	

	@PreUpdate
	public void preUpdate() {
		updatedAt = LocalDateTime.now(); 
	}



	public UUID getProfileId() {
		return profileId;
	}



	public void setProfileId(UUID profileId) {
		this.profileId = profileId;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public LocalDateTime getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}



	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}



	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}



	public Device getDevice() {
		return device;
	}



	public void setDevice(Device device) {
		this.device = device;
	}



	public Preference getPreference() {
		return preference;
	}



	public void setPreference(Preference preference) {
		this.preference = preference;
	}
	

}
