package com.sptrans.mobilidade_urbana.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name="alerts")
public class Alert {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long alertId;
	private Integer minutesBefore;
	private Boolean active;
	private LocalDateTime createdAt;
	
	@ManyToOne
	@JoinColumn(name="profile_id")
	private Profile profile;
	
	public Alert () {}

	public Alert(Long alertId, Integer minutesBefore, Boolean active, LocalDateTime createdAt, Profile profile) {
		super();
		this.alertId = alertId;
		this.minutesBefore = minutesBefore;
		this.active = active;
		this.createdAt = createdAt;
		this.profile = profile;
	}
	
	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
	}

	public Long getAlertId() {
		return alertId;
	}

	public void setAlertId(Long alertId) {
		this.alertId = alertId;
	}

	public Integer getMinutesBefore() {
		return minutesBefore;
	}

	public void setMinutesBefore(Integer minutesBefore) {
		this.minutesBefore = minutesBefore;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
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
