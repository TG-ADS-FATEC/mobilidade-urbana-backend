package com.sptrans.mobilidade_urbana.dto;

import com.sptrans.mobilidade_urbana.domain.gtfs.GTFSTime;

public class StopTimeDTO {
	
	private String tripId;
	private GTFSTime arrivalTime;
	private GTFSTime departureTime;
	private String stopId;
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
