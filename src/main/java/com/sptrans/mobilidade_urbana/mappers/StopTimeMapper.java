package com.sptrans.mobilidade_urbana.mappers;

import org.springframework.stereotype.Component;

import com.sptrans.mobilidade_urbana.domain.gtfs.GTFSTime;
import com.sptrans.mobilidade_urbana.dto.StopTimeDTO;
import com.sptrans.mobilidade_urbana.entities.Stop;
import com.sptrans.mobilidade_urbana.entities.StopTime;
import com.sptrans.mobilidade_urbana.entities.StopTimeId;
import com.sptrans.mobilidade_urbana.entities.Trip;

import jakarta.persistence.EntityManager;

@Component
public class StopTimeMapper {
	
	private final EntityManager entityManager;

	public StopTimeMapper(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}
	
	public StopTime toEntity(StopTimeDTO dto) {
		
		StopTime stopTime = new StopTime();
		
		stopTime.setStopTimeId(new StopTimeId(dto.getTripId(), dto.getStopSequence()));
		
		stopTime.setTrip(entityManager.getReference(Trip.class,dto.getTripId()));
		
		stopTime.setStop(entityManager.getReference(Stop.class, dto.getStopId()));
		
		stopTime.setArrivalTime(GTFSTime.parse(dto.getArrivalTime()));
		
		stopTime.setDepartureTime(GTFSTime.parse(dto.getDepartureTime()));
		
		return stopTime;
	}

}
