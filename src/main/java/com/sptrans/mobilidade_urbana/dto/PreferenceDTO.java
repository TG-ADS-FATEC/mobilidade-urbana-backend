package com.sptrans.mobilidade_urbana.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.sptrans.mobilidade_urbana.entities.Preference;
import com.sptrans.mobilidade_urbana.entities.RoutePreference;
import com.sptrans.mobilidade_urbana.entities.TransportType;

public class PreferenceDTO {
	
	private Long preferenceId;
	private Set<TransportType> transportTypes = new HashSet<>();
	private RoutePreference routePreference;
	private Boolean slowPace;
	private Integer maxWalkingTime;
	private LocalDateTime updatedAt;
	
	public PreferenceDTO(){}

	public PreferenceDTO(Long preferenceId, Set<TransportType> transportTypes, RoutePreference routePreference,
			Boolean slowPace, Integer maxWalkingTime, LocalDateTime updatedAt) {
		super();
		this.preferenceId = preferenceId;
		this.transportTypes = transportTypes;
		this.routePreference = routePreference;
		this.slowPace = slowPace;
		this.maxWalkingTime = maxWalkingTime;
		this.updatedAt = updatedAt;
	}
	
	public PreferenceDTO(Preference entity) {
		preferenceId = entity.getPreferenceId();
		transportTypes = entity.getTransportTypes();
		routePreference = entity.getRoutePreference();
		slowPace = entity.getSlowPace();
		maxWalkingTime = entity.getMaxWalkingTime();
		updatedAt = entity.getUpdatedAt();
	}

	public Long getPreferenceId() {
		return preferenceId;
	}

	public Set<TransportType> getTransportTypes() {
		return transportTypes;
	}

	public RoutePreference getRoutePreference() {
		return routePreference;
	}

	public Boolean getSlowPace() {
		return slowPace;
	}

	public Integer getMaxWalkingTime() {
		return maxWalkingTime;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	
	

}
