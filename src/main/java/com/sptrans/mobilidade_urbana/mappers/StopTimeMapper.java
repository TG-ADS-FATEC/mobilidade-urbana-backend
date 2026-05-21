package com.sptrans.mobilidade_urbana.mappers;

import org.springframework.stereotype.Component;

import com.sptrans.mobilidade_urbana.domain.gtfs.GTFSTime;
import com.sptrans.mobilidade_urbana.dto.StopTimeRawDTO;
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
	
	public StopTime toEntity(StopTimeRawDTO rawDto) {
		
		if(rawDto==null) {
			return null;
		}
		
		StopTime stopTime = new StopTime();
		
		stopTime.setStopTimeId(new StopTimeId(rawDto.getTripId(), Integer.parseInt(rawDto.getStopSequence())));
		
		stopTime.setTrip(entityManager.getReference(Trip.class,rawDto.getTripId()));
		
		stopTime.setStop(entityManager.getReference(Stop.class, rawDto.getStopId()));
		
		stopTime.setArrivalTime(GTFSTime.parse(rawDto.getArrivalTime()));
		
		stopTime.setDepartureTime(GTFSTime.parse(rawDto.getDepartureTime()));
		
		return stopTime;
	}

}
