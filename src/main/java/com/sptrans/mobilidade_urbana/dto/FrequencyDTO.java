package com.sptrans.mobilidade_urbana.dto;

import com.sptrans.mobilidade_urbana.domain.gtfs.GTFSTime;

public class FrequencyDTO {
	
	private String tripId;
	private GTFSTime startTime;
	private GTFSTime endTime;
	private Integer headwaySeconds;
	
	public FrequencyDTO() {}

	public FrequencyDTO(String tripId, GTFSTime startTime, GTFSTime endTime, Integer headwaySeconds) {
		super();
		this.tripId = tripId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.headwaySeconds = headwaySeconds;
	}

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public GTFSTime getStartTime() {
		return startTime;
	}

	public void setStartTime(GTFSTime startTime) {
		this.startTime = startTime;
	}

	public GTFSTime getEndTime() {
		return endTime;
	}

	public void setEndTime(GTFSTime endTime) {
		this.endTime = endTime;
	}

	public Integer getHeadwaySeconds() {
		return headwaySeconds;
	}

	public void setHeadwaySeconds(Integer headwaySeconds) {
		this.headwaySeconds = headwaySeconds;
	}
	
	

}
