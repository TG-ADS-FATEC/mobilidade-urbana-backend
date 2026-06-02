package com.sptrans.mobilidade_urbana.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.sptrans.mobilidade_urbana.entities.RoutePreference;
import com.sptrans.mobilidade_urbana.entities.TransportType;

import io.swagger.v3.oas.annotations.media.Schema;

public class PreferenceDTO {
	
	@Schema(description="Tipos de transporte público a serem utilizados")
	private Set<TransportType> transportTypes = new HashSet<>();
	@Schema(description="Preferência para o cálculo da rota", example="FASTEST")
	private RoutePreference routePreference;
	@Schema(description="Opção para quem possui mobilidade reduzida para o cálculo da rota", example="false")
	private Boolean slowPace;
	@Schema(description="Tempo máximo de caminhada para o cálculo da rota", example="20")
	private Integer maxWalkingTime;
	@Schema(description = "Data de criação da preferência", example = "2026-05-07T22:09:49.22619787")
	private LocalDateTime createdAt;
	@Schema(description = "Data de atualização da preferência", example = "2026-05-07T22:09:49.22619787")
	private LocalDateTime updatedAt;
	
	public PreferenceDTO(){}

	public PreferenceDTO(Set<TransportType> transportTypes, RoutePreference routePreference, Boolean slowPace,
			Integer maxWalkingTime, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.transportTypes = transportTypes;
		this.routePreference = routePreference;
		this.slowPace = slowPace;
		this.maxWalkingTime = maxWalkingTime;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
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
	
	
}
