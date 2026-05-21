package com.sptrans.mobilidade_urbana.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StopRawDTO {
	
	@JsonProperty("stop_id")
	private String stopId;
	@JsonProperty("stop_name")
	private String stopName;
	@JsonProperty("stop_desc")
	private String stopDescription;
	@JsonProperty("stop_lat")
	private String stopLatitude;
	@JsonProperty("stop_lon")
	private String stopLongitude;
	
	public StopRawDTO() {}

	public StopRawDTO(String stopId, String stopName, String stopDescription, String stopLatitude,
			String stopLongitude) {
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

	public String getStopLatitude() {
		return stopLatitude;
	}

	public void setStopLatitude(String stopLatitude) {
		this.stopLatitude = stopLatitude;
	}

	public String getStopLongitude() {
		return stopLongitude;
	}

	public void setStopLongitude(String stopLongitude) {
		this.stopLongitude = stopLongitude;
	}
	
	

}
