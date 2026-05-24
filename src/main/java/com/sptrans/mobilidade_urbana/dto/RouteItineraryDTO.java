package com.sptrans.mobilidade_urbana.dto;

import java.util.List;

public class RouteItineraryDTO {
	
	String routeId;
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
