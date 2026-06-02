package com.sptrans.mobilidade_urbana.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name="preferences")
public class Preference {
	
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	@Column(updatable = false, nullable=false)
	private UUID preferenceId;
	@ElementCollection(targetClass = TransportType.class)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "preference_transport_types", joinColumns = @JoinColumn(name = "preference_id"))
	@Column(name = "transport_type")
	private Set<TransportType> transportTypes = new HashSet<>();
	@Enumerated(EnumType.STRING)
	private RoutePreference routePreference;
	private Boolean slowPace;
	private Integer maxWalkingTime;
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private LocalDateTime createdAt;
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private LocalDateTime updatedAt;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="device_id", nullable=false, unique=true)
	private Device device;
	
	@OneToOne(mappedBy="preference", cascade=CascadeType.ALL)
	private Profile profile;
	
	public Preference() {}
	
	public Preference(UUID preferenceId, Set<TransportType> transportTypes, RoutePreference routePreference,
			Boolean slowPace, Integer maxWalkingTime, LocalDateTime createdAt, LocalDateTime updatedAt, Device device) {
		super();
		this.preferenceId = preferenceId;
		this.transportTypes = transportTypes;
		this.routePreference = routePreference;
		this.slowPace = slowPace;
		this.maxWalkingTime = maxWalkingTime;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.device = device;
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

	public UUID getPreferenceId() {
		return preferenceId;
	}

	public void setPreferenceId(UUID preferenceId) {
		this.preferenceId = preferenceId;
	}

	public Set<TransportType> getTransportTypes() {
		return transportTypes;
	}

	public void setTransportTypes(Set<TransportType> transportTypes) {
		this.transportTypes = transportTypes;
	}

	public RoutePreference getRoutePreference() {
		return routePreference;
	}

	public void setRoutePreference(RoutePreference routePreference) {
		this.routePreference = routePreference;
	}

	public Boolean getSlowPace() {
		return slowPace;
	}

	public void setSlowPace(Boolean slowPace) {
		this.slowPace = slowPace;
	}

	public Integer getMaxWalkingTime() {
		return maxWalkingTime;
	}

	public void setMaxWalkingTime(Integer maxWalkingTime) {
		this.maxWalkingTime = maxWalkingTime;
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

	public Profile getProfile() {
		return profile;
	}
	
	
}
