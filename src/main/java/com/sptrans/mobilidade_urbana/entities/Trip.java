package com.sptrans.mobilidade_urbana.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="trips")
public class Trip {
	
	@Id
	private String tripId;
	private String tripHeadsign;
	private DirectionId directionId;
	
	@ManyToOne
	@JoinColumn(name="route_id", nullable=false, unique=true)
	private Route route;
	
	@ManyToOne
	@JoinColumn(name="service_id", nullable=false, unique=true)
	private Calendar calendar;
	
	@ManyToOne
	@JoinColumn(name="shape_id", nullable=false, unique=true)
	private Shape shape;
	
	public Trip() {}

	public Trip(String tripId, String tripHeadsign, DirectionId directionId, Route route,
			com.sptrans.mobilidade_urbana.entities.Calendar calendar, Shape shape) {
		super();
		this.tripId = tripId;
		this.tripHeadsign = tripHeadsign;
		this.directionId = directionId;
		this.route = route;
		this.calendar = calendar;
		this.shape = shape;
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

	public DirectionId getDirectionId() {
		return directionId;
	}

	public void setDirectionId(DirectionId directionId) {
		this.directionId = directionId;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}
	
	
}
