package com.sptrans.mobilidade_urbana.mappers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.sptrans.mobilidade_urbana.dto.CalendarDTO;
import com.sptrans.mobilidade_urbana.dto.CalendarRawDTO;
import com.sptrans.mobilidade_urbana.entities.Calendar;

@Component
public class CalendarMapper {
	
	public Calendar toEntity(CalendarRawDTO rawDto) {
		if(rawDto==null) {
			return null;
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		Calendar calendar = new Calendar();
		calendar.setServiceId(rawDto.getServiceId());
		calendar.setMonday(parseBoolean(rawDto.getMonday()));
		calendar.setTuesday(parseBoolean(rawDto.getTuesday()));
		calendar.setWednesday(parseBoolean(rawDto.getWednesday()));
		calendar.setThursday((parseBoolean(rawDto.getThursday())));
		calendar.setFriday(parseBoolean(rawDto.getFriday()));
		calendar.setSaturday(parseBoolean(rawDto.getSaturday()));
		calendar.setSunday(parseBoolean(rawDto.getSunday()));
		calendar.setStartDate(LocalDate.parse(rawDto.getStartDate(), formatter));
		calendar.setEndDate(LocalDate.parse(rawDto.getEndDate(), formatter));
		
		return calendar;
	}
	
	private Boolean parseBoolean(String value) {
		return "1".equals(value) || "true".equalsIgnoreCase(value);
	}
	
	public CalendarDTO toDTO(Calendar entity) {
		
		if(entity==null) return null;
		
		return new CalendarDTO(
				entity.getServiceId(),
				entity.getMonday(),
				entity.getTuesday(),
				entity.getWednesday(),
				entity.getThursday(),
				entity.getFriday(),
				entity.getSaturday(),
				entity.getSunday(),
				entity.getStartDate(),
				entity.getEndDate());
	}

}
