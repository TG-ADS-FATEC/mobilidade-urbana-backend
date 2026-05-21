package com.sptrans.mobilidade_urbana.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FrequencyRawDTO {
	
	@JsonProperty("trip_id")
	private String tripId;
	@JsonProperty("start_time")
	private String startTime;
	@JsonProperty("end_time")
	private String endTime;
	@JsonProperty("headway_secs")
	private String headwaySeconds;
	
	public FrequencyRawDTO() {}

	public FrequencyRawDTO(String tripId, String startTime, String endTime, String headwaySeconds) {
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getHeadwaySeconds() {
		return headwaySeconds;
	}

	public void setHeadwaySeconds(String headwaySeconds) {
		this.headwaySeconds = headwaySeconds;
	}
	
	

}
