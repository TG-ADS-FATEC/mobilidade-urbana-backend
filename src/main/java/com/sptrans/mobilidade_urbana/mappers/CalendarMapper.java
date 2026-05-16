package com.sptrans.mobilidade_urbana.mappers;

import org.springframework.stereotype.Component;

import com.sptrans.mobilidade_urbana.dto.CalendarDTO;
import com.sptrans.mobilidade_urbana.entities.Calendar;

@Component
public class CalendarMapper {
	
	public Calendar toEntity(CalendarDTO dto) {
		if(dto==null) {
			return null;
		}
		
		Calendar calendar = new Calendar();
		calendar.setServiceId(dto.getServiceId());
		calendar.setMonday(dto.getMonday());
		calendar.setTuesday(dto.getTuesday());
		calendar.setWednesday(dto.getWednesday());
		calendar.setThursday(dto.getThursday());
		calendar.setFriday(dto.getFriday());
		calendar.setStartDate(dto.getStartDate());
		calendar.setEndDate(dto.getEndDate());
		
		return calendar;
	}

}
