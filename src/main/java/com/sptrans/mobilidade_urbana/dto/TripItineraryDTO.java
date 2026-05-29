package com.sptrans.mobilidade_urbana.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public class TripItineraryDTO {
	
	@Schema(description = "Id da viagem", example = "1012-10-0")
	String tripId;
	@Schema(description = "Lista de paradas")
	List<StopTimeItineraryDTO> stops;
	
	public TripItineraryDTO() {}

	public TripItineraryDTO(String tripId, List<StopTimeItineraryDTO> stops) {
		super();
		this.tripId = tripId;
		this.stops = stops;
	}

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public List<StopTimeItineraryDTO> getStops() {
		return stops;
	}

	public void setStops(List<StopTimeItineraryDTO> stops) {
		this.stops = stops;
	}
	
	

}
