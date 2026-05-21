package com.sptrans.mobilidade_urbana.entities;

import com.sptrans.mobilidade_urbana.domain.gtfs.GTFSTime;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name="stop_times")
public class StopTime {
	
	@EmbeddedId
	private StopTimeId stopTimeId;
	
	@Embedded
	@AttributeOverride(
			name="secondsFromMidnight",
			column= @Column(name="arrivalTime")
			)
	private GTFSTime arrivalTime;
	
	@Embedded
	@AttributeOverride(
			name="secondsFromMidnight",
			column= @Column(name="departureTime")
			)
	private GTFSTime departureTime;
	
	@ManyToOne
	@MapsId("tripId")
	@JoinColumn(name="trip_id")
	private Trip trip;
	
	@ManyToOne
	@JoinColumn(name="stop_id", nullable=false)
	private Stop stop;
	
	public StopTime() {}

	public StopTime(StopTimeId stopTimeId, GTFSTime arrivalTime, GTFSTime departureTime,
			Trip trip, Stop stop) {
		super();
		this.stopTimeId = stopTimeId;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.trip = trip;
		this.stop = stop;
	}

	public StopTimeId getStopTimeId() {
		return stopTimeId;
	}

	public void setStopTimeId(StopTimeId stopTimeId) {
		this.stopTimeId = stopTimeId;
	}

	public GTFSTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(GTFSTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public GTFSTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(GTFSTime departureTime) {
		this.departureTime = departureTime;
	}

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public Stop getStop() {
		return stop;
	}

	public void setStop(Stop stop) {
		this.stop = stop;
	}
	
	

}
