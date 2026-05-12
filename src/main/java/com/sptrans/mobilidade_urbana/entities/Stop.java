package com.sptrans.mobilidade_urbana.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Stop {
	
	@Id
	private Long stopId;
	private String stopName;
	private String stopDescription;
	private Double stopLatitude;
	private Double stopLongitude;
	
	public Stop() {}

	public Stop(Long stopId, String stopName, String stopDescription, Double stopLatitude, Double stopLongitude) {
		super();
		this.stopId = stopId;
		this.stopName = stopName;
		this.stopDescription = stopDescription;
		this.stopLatitude = stopLatitude;
		this.stopLongitude = stopLongitude;
	}

	public Long getStopId() {
		return stopId;
	}

	public void setStopId(Long stopId) {
		this.stopId = stopId;
	}

	public String getStopName() {
		return stopName;
	}

	public void setStopName(String stopName) {
		this.stopName = stopName;
	}

	public String getStopDescription() {
		return stopDescription;
	}

	public void setStopDescription(String stopDescription) {
		this.stopDescription = stopDescription;
	}

	public Double getStopLatitude() {
		return stopLatitude;
	}

	public void setStopLatitude(Double stopLatitude) {
		this.stopLatitude = stopLatitude;
	}

	public Double getStopLongitude() {
		return stopLongitude;
	}

	public void setStopLongitude(Double stopLongitude) {
		this.stopLongitude = stopLongitude;
	}
	
	

}
