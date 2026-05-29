package com.sptrans.mobilidade_urbana.dto;

import com.sptrans.mobilidade_urbana.gtfs.GTFSTime;

import io.swagger.v3.oas.annotations.media.Schema;

public class StopTimeDTO {
	
	@Schema(description = "Id da viagem", example = "1012-10-0")
	private String tripId;
	@Schema(description = "Tempo de chegada na parada", example = "07:00:00")
	private GTFSTime arrivalTime;
	@Schema(description = "Tempo de saída na parada", example = "07:00:00")
	private GTFSTime departureTime;
	@Schema(description = "Id da parada", example = "301790")
	private String stopId;
	@Schema(description = "Sequência das paradas", example = "1")
	private Integer stopSequence;
	
	public StopTimeDTO() {}

	public StopTimeDTO(String tripId, GTFSTime arrivalTime, GTFSTime departureTime, String stopId, Integer stopSequence) {
		super();
		this.tripId = tripId;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.stopId = stopId;
		this.stopSequence = stopSequence;
	}

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public GTFSTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(GTFSTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public GTFSTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(GTFSTime departureTime) {
		this.departureTime = departureTime;
	}

	public String getStopId() {
		return stopId;
	}

	public void setStopId(String stopId) {
		this.stopId = stopId;
	}

	public Integer getStopSequence() {
		return stopSequence;
	}

	public void setStopSequence(Integer stopSequence) {
		this.stopSequence = stopSequence;
	}
	
	

}
