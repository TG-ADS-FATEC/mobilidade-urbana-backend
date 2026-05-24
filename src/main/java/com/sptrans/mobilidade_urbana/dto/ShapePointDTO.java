package com.sptrans.mobilidade_urbana.dto;

public class ShapePointDTO {
	
	private Integer sequence;
	
	private Double latitude;
	
	private Double longitude;
	
	private Double distanceTraveled;
	
	public ShapePointDTO() {}

	public ShapePointDTO(Integer sequence, Double latitude, Double longitude, Double distanceTraveled) {
		super();
		this.sequence = sequence;
		this.latitude = latitude;
		this.longitude = longitude;
		this.distanceTraveled = distanceTraveled;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getDistanceTraveled() {
		return distanceTraveled;
	}

	public void setDistanceTraveled(Double distanceTraveled) {
		this.distanceTraveled = distanceTraveled;
	}
	
	

}
