package com.sptrans.mobilidade_urbana.gtfs.importers;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sptrans.mobilidade_urbana.dto.StopTimeRawDTO;
import com.sptrans.mobilidade_urbana.entities.StopTime;
import com.sptrans.mobilidade_urbana.gtfs.GTFSImporter;
import com.sptrans.mobilidade_urbana.mappers.StopTimeMapper;

@Component
public class StopTimeImporter implements GTFSImporter<StopTime> {
	
	private final ObjectMapper objectMapper;
	private final StopTimeMapper stopTimeMapper;
	
	public StopTimeImporter(ObjectMapper objectMapper, StopTimeMapper stopTimeMapper) {
		super();
		this.objectMapper = objectMapper;
		this.stopTimeMapper = stopTimeMapper;
	}
	
	@Override
	public String fileName() {
		return "stop_times.txt";
	}
	
	@Override
	public StopTime mapRow(Map<String, String> row) {
		
		StopTimeRawDTO dto = objectMapper.convertValue(row, StopTimeRawDTO.class);
		
		return stopTimeMapper.toEntity(dto);
	}

}
