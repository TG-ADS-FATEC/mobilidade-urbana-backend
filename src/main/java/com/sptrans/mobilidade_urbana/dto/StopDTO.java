package com.sptrans.mobilidade_urbana.dto;

import com.sptrans.mobilidade_urbana.entities.Stop;

public class StopDTO {
	
	private String stopId;
	private String stopName;
	private String stopDescription;
	private Double stopLatitude;
	private Double stopLongitude;
	
	public StopDTO() {}
	
	public StopDTO(String stopId, String stopName, String stopDescription, Double stopLatitude, Double stopLongitude) {
		super();
		this.stopId = stopId;
		this.stopName = stopName;
		this.stopDescription = stopDescription;
		this.stopLatitude = stopLatitude;
		this.stopLongitude = stopLongitude;
	}
	
	public StopDTO(Stop entity) {
		stopId = entity.getStopId();
		stopName = entity.getStopName();
		stopDescription = entity.getStopDescription();
		stopLatitude = entity.getStopLatitude();
		stopLongitude = entity.getStopLongitude();
	}
	
	public String getStopId() {
		return stopId;
	}
	public String getStopName() {
		return stopName;
	}
	public String getStopDescription() {
		return stopDescription;
	}
	public Double getStopLatitude() {
		return stopLatitude;
	}
	public Double getStopLongitude() {
		return stopLongitude;
	}
	
	

}
