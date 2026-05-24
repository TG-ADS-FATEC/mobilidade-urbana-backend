package com.sptrans.mobilidade_urbana.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.sptrans.mobilidade_urbana.domain.gtfs.GTFSTime;
import com.sptrans.mobilidade_urbana.dto.StopTimeDTO;
import com.sptrans.mobilidade_urbana.dto.StopTimeRawDTO;
import com.sptrans.mobilidade_urbana.entities.Stop;
import com.sptrans.mobilidade_urbana.entities.StopTime;
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
		
		stopTime.setTrip(entityManager.getReference(Trip.class,rawDto.getTripId()));
		
		stopTime.setStop(entityManager.getReference(Stop.class, rawDto.getStopId()));
		
		stopTime.setArrivalTime(GTFSTime.parse(rawDto.getArrivalTime()));
		
		stopTime.setDepartureTime(GTFSTime.parse(rawDto.getDepartureTime()));
		
		stopTime.setStopSequence(Integer.parseInt(rawDto.getStopSequence()));
		
		return stopTime;
	}
	
	public StopTimeDTO toDTO(StopTime entity) {
		
		if(entity==null) return null;
		
		return new StopTimeDTO(
				entity.getTrip().getTripId(),
				entity.getArrivalTime(),
				entity.getDepartureTime(),
				entity.getStop().getStopId(),
				entity.getStopSequence());
	}
	
	public List<StopTimeDTO> toDTOList(List<StopTime> entities) {
		if(entities == null || entities.isEmpty()) {
			return new ArrayList<>();
		}
		
		return entities.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}
	
	public Page<StopTimeDTO> toDTOPage(Page<StopTime> page) {
		return page.map(this::toDTO);
	}

}
