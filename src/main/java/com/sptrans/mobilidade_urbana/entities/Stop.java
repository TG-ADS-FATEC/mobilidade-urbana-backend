package com.sptrans.mobilidade_urbana.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;

@Entity
public class Stop {
	
	@Id
	private String stopId;
	private String stopName;
	private String stopDescription;
	private Double stopLatitude;
	private Double stopLongitude;
	
	@OneToMany(mappedBy="stop")
	@OrderBy("arrivalTime.secondsFromMidnight ASC")
	private List<StopTime> stopTimes = new ArrayList<>();
	
	
	public Stop() {}

	public Stop(String stopId, String stopName, String stopDescription, Double stopLatitude, Double stopLongitude) {
		super();
		this.stopId = stopId;
		this.stopName = stopName;
		this.stopDescription = stopDescription;
		this.stopLatitude = stopLatitude;
		this.stopLongitude = stopLongitude;
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

	public List<StopTime> getStopTimes() {
		return stopTimes;
	}
	
	

}
