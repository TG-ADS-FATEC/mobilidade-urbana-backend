package com.sptrans.mobilidade_urbana.dto;

public class FrequencyDTO {
	
	private String tripId;
	private String startTime;
	private String endTime;
	private Integer headwaySeconds;
	
	public FrequencyDTO() {}

	public FrequencyDTO(String tripId, String startTime, String endTime, Integer headwaySeconds) {
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

	public Integer getHeadwaySeconds() {
		return headwaySeconds;
	}

	public void setHeadwaySeconds(Integer headwaySeconds) {
		this.headwaySeconds = headwaySeconds;
	}
	
	

}
