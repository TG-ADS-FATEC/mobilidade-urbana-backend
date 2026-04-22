package com.sptrans.mobilidade_urbana.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
@Table(name="preference")
public class Preference {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long preferenceId;
	@ElementCollection(targetClass = TransportType.class)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "preference_transport_types", joinColumns = @JoinColumn(name = "preference_id"))
	@Column(name = "transport_type")
	private Set<TransportType> transportTypes = new HashSet<>();
	@Enumerated(EnumType.STRING)
	private RoutePreference routePreference;
	private Boolean slowPace;
	private Integer maxWalkingTime;
	private LocalDateTime updatedAt;
	
	@OneToOne(mappedBy="preference", cascade=CascadeType.ALL)
	private User user;
	
	public Preference() {}
	

	public Preference(Long preferenceId, Set<TransportType> transportTypes, RoutePreference routePreference,
			Boolean slowPace, Integer maxWalkingTime, LocalDateTime updatedAt) {
		super();
		this.preferenceId = preferenceId;
		this.transportTypes = transportTypes;
		this.routePreference = routePreference;
		this.slowPace = slowPace;
		this.maxWalkingTime = maxWalkingTime;
		this.updatedAt = updatedAt;
	}
	
	@PrePersist
	public void prePersist() {
		updatedAt = LocalDateTime.now(); 
	}

	@PreUpdate
	public void preUpdate() {
		updatedAt = LocalDateTime.now(); 
	}


	public Long getPreferenceId() {
		return preferenceId;
	}

	public void setPreferenceId(Long preferenceId) {
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

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	

}
