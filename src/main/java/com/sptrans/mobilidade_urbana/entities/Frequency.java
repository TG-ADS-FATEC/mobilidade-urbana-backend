package com.sptrans.mobilidade_urbana.entities;

import com.sptrans.mobilidade_urbana.gtfs.GTFSTime;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name="frequencies")
public class Frequency {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long frequencyId;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(
				name = "secondsFromMidnight",
				column = @Column(name= "start_time_seconds")
				)
	})
	private GTFSTime startTime;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(
				name = "secondsFromMidnight",
				column = @Column(name= "end_time_seconds")
				)
	})
	private GTFSTime endTime;
	
	private Integer headwaySeconds;
	
	@ManyToOne
	@JoinColumn(name="trip_id", nullable=false)
	private Trip trip;
	
	public Frequency() {}

	public Frequency(Long frequencyId, GTFSTime startTime, GTFSTime endTime, Integer headwaySeconds, Trip trip) {
		super();
		this.frequencyId = frequencyId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.headwaySeconds = headwaySeconds;
		this.trip = trip;
	}

	public Long getFrequencyId() {
		return frequencyId;
	}

	public void setFrequencyId(Long frequencyId) {
		this.frequencyId = frequencyId;
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

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}
	
	

}
