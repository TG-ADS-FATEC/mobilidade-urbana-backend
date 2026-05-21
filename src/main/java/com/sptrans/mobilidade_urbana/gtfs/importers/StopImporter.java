package com.sptrans.mobilidade_urbana.gtfs.importers;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sptrans.mobilidade_urbana.dto.StopRawDTO;
import com.sptrans.mobilidade_urbana.entities.Stop;
import com.sptrans.mobilidade_urbana.gtfs.GTFSImporter;
import com.sptrans.mobilidade_urbana.mappers.StopMapper;

@Component
public class StopImporter implements GTFSImporter<Stop> {
	
	private final ObjectMapper objectMapper;
	private final StopMapper stopMapper;
	
	public StopImporter(ObjectMapper objectMapper, StopMapper stopMapper) {
		super();
		this.objectMapper = objectMapper;
		this.stopMapper = stopMapper;
	}
	
	@Override
	public String fileName() {
		return "stops.txt";
	}
	
	@Override
	public Stop mapRow(Map<String, String> row) {
		
		StopRawDTO dto = objectMapper.convertValue(row, StopRawDTO.class);
		
		return stopMapper.toEntity(dto);
	}

}
