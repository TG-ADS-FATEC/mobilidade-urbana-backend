package com.sptrans.mobilidade_urbana.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class StopTimeId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String tripId;
	private Integer stopSequence;
	
	public StopTimeId() {}

	public StopTimeId(String tripId, Integer stopSequence) {
		super();
		this.tripId = tripId;
		this.stopSequence = stopSequence;
	}

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public Integer getStopSequence() {
		return stopSequence;
	}

	public void setStopSequence(Integer stopSequence) {
		this.stopSequence = stopSequence;
	}

	@Override
	public int hashCode() {
		return Objects.hash(stopSequence, tripId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StopTimeId other = (StopTimeId) obj;
		return Objects.equals(stopSequence, other.stopSequence) && Objects.equals(tripId, other.tripId);
	}
	
	

}
