package com.sptrans.mobilidade_urbana.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.sptrans.mobilidade_urbana.domain.gtfs.GTFSTime;
import com.sptrans.mobilidade_urbana.dto.FrequencyDTO;
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
	
	public FrequencyDTO toDTO(Frequency entity) {
		
		if(entity==null) return null;
		
		return new FrequencyDTO(
				entity.getTrip().getTripId(),
				entity.getStartTime(),
				entity.getEndTime(),
				entity.getHeadwaySeconds());
	}
	
	public List<FrequencyDTO> toDTOList(List<Frequency> entities) {
		if(entities == null || entities.isEmpty()) {
			return new ArrayList<>();
		}
		
		return entities.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}
	
	public Page<FrequencyDTO> toDTOPage(Page<Frequency> page) {
		return page.map(this::toDTO);
	}
	
}
