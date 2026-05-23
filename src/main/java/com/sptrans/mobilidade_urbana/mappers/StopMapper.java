package com.sptrans.mobilidade_urbana.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.sptrans.mobilidade_urbana.dto.StopDTO;
import com.sptrans.mobilidade_urbana.dto.StopRawDTO;
import com.sptrans.mobilidade_urbana.entities.Stop;

@Component
public class StopMapper {
	
	public Stop toEntity(StopRawDTO rawDto) {
		if(rawDto==null) {
			return null;
		}
		
		Stop stop = new Stop();
		stop.setStopId(rawDto.getStopId());
		stop.setStopName(rawDto.getStopName());
		stop.setStopDescription(rawDto.getStopDescription());
		stop.setStopLatitude(Double.parseDouble(rawDto.getStopLatitude()));
		stop.setStopLongitude(Double.parseDouble(rawDto.getStopLongitude()));
		
		return stop;
	}
	
	public StopDTO toDTO(Stop entity) {
		
		if(entity==null) return null;
		
		return new StopDTO(
				entity.getStopId(),
				entity.getStopName(),
				entity.getStopDescription(),
				entity.getStopLatitude(),
				entity.getStopLongitude());
	}
	
	public List<StopDTO> toDTOList(List<Stop> entities) {
		if(entities == null || entities.isEmpty()) {
			return new ArrayList<>();
		}
		
		return entities.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}
	
	public Page<StopDTO> toDTOPage(Page<Stop> page) {
		return page.map(this::toDTO);
	}

}
