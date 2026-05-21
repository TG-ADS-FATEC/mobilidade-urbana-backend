package com.sptrans.mobilidade_urbana.dto;

public class TripRawDTO {
	
	private String tripId;
	private String tripHeadsign;
	private String directionId;
	private String routeId;
	private String serviceId;
	private String shapeId;
	
	public TripRawDTO() {}

	public TripRawDTO(String tripId, String tripHeadsign, String directionId, String routeId, String serviceId,
			String shapeId) {
		super();
		this.tripId = tripId;
		this.tripHeadsign = tripHeadsign;
		this.directionId = directionId;
		this.routeId = routeId;
		this.serviceId = serviceId;
		this.shapeId = shapeId;
	}

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public String getTripHeadsign() {
		return tripHeadsign;
	}

	public void setTripHeadsign(String tripHeadsign) {
		this.tripHeadsign = tripHeadsign;
	}

	public String getDirectionId() {
		return directionId;
	}

	public void setDirectionId(String directionId) {
		this.directionId = directionId;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getShapeId() {
		return shapeId;
	}

	public void setShapeId(String shapeId) {
		this.shapeId = shapeId;
	}
	
	

}
