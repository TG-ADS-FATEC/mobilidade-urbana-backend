package com.sptrans.mobilidade_urbana.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShapeRawDTO {
	
	@JsonProperty("shape_id")
	String shapeId;
	
	@JsonProperty("shape_pt_lat")
	String latitude;
	
	@JsonProperty("shape_pt_lon")
	String longitude;
	
	@JsonProperty("shape_pt_sequence")
	String sequence;
	
	@JsonProperty("shape_dist_traveled")
	String distanceTraveled;
	
	public ShapeRawDTO() {}

	public ShapeRawDTO(String shapeId, String latitude, String longitude, String sequence, String distanceTraveled) {
		super();
		this.shapeId = shapeId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.sequence = sequence;
		this.distanceTraveled = distanceTraveled;
	}

	public String getShapeId() {
		return shapeId;
	}

	public void setShapeId(String shapeId) {
		this.shapeId = shapeId;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getDistanceTraveled() {
		return distanceTraveled;
	}

	public void setDistanceTraveled(String distanceTraveled) {
		this.distanceTraveled = distanceTraveled;
	}
	
	

}
