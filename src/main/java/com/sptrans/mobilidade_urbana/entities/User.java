package com.sptrans.mobilidade_urbana.entities;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
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
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long userId;
	private String email;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="device_id", nullable=false, unique=true)
	private Device device;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="preference_id", nullable=false, unique=true)
	private Preference preference;
	
	public User() {}

	

	public User(Long userId, String email, LocalDateTime createdAt, LocalDateTime updatedAt, Device device,
			Preference preference) {
		super();
		this.userId = userId;
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



	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
		this.userId = userId;
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
