package com.sptrans.mobilidade_urbana.mappers;

import org.springframework.stereotype.Component;

import com.sptrans.mobilidade_urbana.domain.gtfs.GTFSTime;
import com.sptrans.mobilidade_urbana.dto.FrequencyRawDTO;
import com.sptrans.mobilidade_urbana.entities.Frequency;
import com.sptrans.mobilidade_urbana.entities.Trip;

import jakarta.persistence.EntityManager;

@Component
public class FrequencyMapper {
	
	private final EntityManager entityManager;

	public FrequencyMapper(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Frequency toEntity(FrequencyRawDTO rawDto) {
		
		Frequency frequency = new Frequency();
		
		Trip trip = entityManager.getReference(Trip.class, rawDto.getTripId());
		frequency.setTrip(trip);
		
		frequency.setStartTime(GTFSTime.parse(rawDto.getStartTime()));
		frequency.setEndTime(GTFSTime.parse(rawDto.getEndTime()));
		frequency.setHeadwaySeconds(Integer.parseInt(rawDto.getHeadwaySeconds()));
		
		return frequency;
	}
	
}
