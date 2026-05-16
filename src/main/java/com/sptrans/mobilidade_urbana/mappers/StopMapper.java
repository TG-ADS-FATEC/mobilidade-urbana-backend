package com.sptrans.mobilidade_urbana.mappers;

import org.springframework.stereotype.Component;

import com.sptrans.mobilidade_urbana.dto.StopDTO;
import com.sptrans.mobilidade_urbana.entities.Stop;

@Component
public class StopMapper {
	
	public Stop toEntity(StopDTO dto) {
		if(dto==null) {
			return null;
		}
		
		Stop stop = new Stop();
		stop.setStopId(dto.getStopId());
		stop.setStopName(dto.getStopName());
		stop.setStopDescription(dto.getStopDescription());
		stop.setStopLatitude(dto.getStopLatitude());
		stop.setStopLongitude(dto.getStopLongitude());
		
		return stop;
	}

}
