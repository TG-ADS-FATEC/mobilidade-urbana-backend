package com.sptrans.mobilidade_urbana.mappers;

import org.springframework.stereotype.Component;

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

}
