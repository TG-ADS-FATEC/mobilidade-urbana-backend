package com.sptrans.mobilidade_urbana.mappers;

import org.springframework.stereotype.Component;

import com.sptrans.mobilidade_urbana.domain.gtfs.GTFSTime;
import com.sptrans.mobilidade_urbana.dto.FrequencyDTO;
import com.sptrans.mobilidade_urbana.entities.Frequency;
import com.sptrans.mobilidade_urbana.entities.Trip;

import jakarta.persistence.EntityManager;

@Component
public class FrequencyMapper {
	
	private final EntityManager entityManager;

	public FrequencyMapper(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Frequency toEntity(FrequencyDTO dto) {
		
		Frequency frequency = new Frequency();
		
		Trip trip = entityManager.getReference(Trip.class, dto.getTripId());
		frequency.setTrip(trip);
		
		frequency.setStartTime(GTFSTime.parse(dto.getStartTime()));
		frequency.setEndTime(GTFSTime.parse(dto.getEndTime()));
		frequency.setHeadwaySeconds(dto.getHeadwaySeconds());
		
		return frequency;
	}
	
}
