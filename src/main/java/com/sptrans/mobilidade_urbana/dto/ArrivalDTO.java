package com.sptrans.mobilidade_urbana.dto;

import com.sptrans.mobilidade_urbana.entities.DirectionId;
import com.sptrans.mobilidade_urbana.gtfs.GTFSTime;

public class ArrivalDTO {
	
	private String routeId;
	private String tripId;
	private String stopId;
	private String stopName;
	private DirectionId directionId;
	private GTFSTime arrivalTime;
	private Integer stopSequence;
	private Integer arrivalInMinutes;
	
	
	public ArrivalDTO() {}


	public ArrivalDTO(String routeId, String tripId, String stopId, String stopName, DirectionId directionId,
			GTFSTime arrivalTime, Integer stopSequence, Integer arrivalInMinutes) {
		super();
		this.routeId = routeId;
		this.tripId = tripId;
		this.stopId = stopId;
		this.stopName = stopName;
		this.directionId = directionId;
		this.arrivalTime = arrivalTime;
		this.stopSequence = stopSequence;
		this.arrivalInMinutes = arrivalInMinutes;
	}


	public String getRouteId() {
		return routeId;
	}


	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}


	public String getTripId() {
		return tripId;
	}


	public void setTripId(String tripId) {
		this.tripId = tripId;
	}


	public String getStopId() {
		return stopId;
	}


	public void setStopId(String stopId) {
		this.stopId = stopId;
	}


	public String getStopName() {
		return stopName;
	}


	public void setStopName(String stopName) {
		this.stopName = stopName;
	}


	public DirectionId getDirectionId() {
		return directionId;
	}


	public void setDirectionId(DirectionId directionId) {
		this.directionId = directionId;
	}


	public GTFSTime getArrivalTime() {
		return arrivalTime;
	}


	public void setArrivalTime(GTFSTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}


	public Integer getStopSequence() {
		return stopSequence;
	}


	public void setStopSequence(Integer stopSequence) {
		this.stopSequence = stopSequence;
	}


	public Integer getArrivalInMinutes() {
		return arrivalInMinutes;
	}


	public void setArrivalInMinutes(Integer arrivalInMinutes) {
		this.arrivalInMinutes = arrivalInMinutes;
	}
	

}
