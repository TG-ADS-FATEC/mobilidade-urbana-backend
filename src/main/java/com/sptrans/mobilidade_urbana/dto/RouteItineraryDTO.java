package com.sptrans.mobilidade_urbana.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public class RouteItineraryDTO {
	
	@Schema(description = "Id da linha", example = "1012-10")
	String routeId;
	@Schema(description = "Lista de viagens")
	List<TripItineraryDTO> trips;
	
	public RouteItineraryDTO() {}

	public RouteItineraryDTO(String routeId, List<TripItineraryDTO> trips) {
		super();
		this.routeId = routeId;
		this.trips = trips;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public List<TripItineraryDTO> getTrips() {
		return trips;
	}

	public void setTrips(List<TripItineraryDTO> trips) {
		this.trips = trips;
	}
	
	

}
