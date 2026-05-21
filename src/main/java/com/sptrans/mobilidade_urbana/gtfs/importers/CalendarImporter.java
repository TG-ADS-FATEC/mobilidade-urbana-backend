package com.sptrans.mobilidade_urbana.gtfs.importers;


import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sptrans.mobilidade_urbana.dto.CalendarRawDTO;
import com.sptrans.mobilidade_urbana.entities.Calendar;
import com.sptrans.mobilidade_urbana.gtfs.GTFSImporter;
import com.sptrans.mobilidade_urbana.mappers.CalendarMapper;

@Component
public class CalendarImporter implements GTFSImporter<Calendar> {
	
	private final ObjectMapper objectMapper;
	private final CalendarMapper calendarMapper;
	
	public CalendarImporter(ObjectMapper objectMapper, CalendarMapper calendarMapper) {
		super();
		this.objectMapper = objectMapper;
		this.calendarMapper = calendarMapper;
	}

	@Override
	public String fileName() {
		return "calendar.txt";
	}

	@Override
	public Calendar mapRow(Map<String, String> row) {
		CalendarRawDTO dto = objectMapper.convertValue(row, CalendarRawDTO.class);
		
		return calendarMapper.toEntity(dto);
	}

}
