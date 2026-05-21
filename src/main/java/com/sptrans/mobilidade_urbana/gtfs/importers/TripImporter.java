package com.sptrans.mobilidade_urbana.gtfs.importers;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sptrans.mobilidade_urbana.dto.TripRawDTO;
import com.sptrans.mobilidade_urbana.entities.Trip;
import com.sptrans.mobilidade_urbana.gtfs.GTFSImporter;
import com.sptrans.mobilidade_urbana.mappers.TripMapper;

@Component
public class TripImporter implements GTFSImporter<Trip> {
	
	private final ObjectMapper objectMapper;
	private final TripMapper tripMapper;
	
	public TripImporter(ObjectMapper objectMapper, TripMapper tripMapper) {
		super();
		this.objectMapper = objectMapper;
		this.tripMapper = tripMapper;
	}
	
	@Override
	public String fileName() {
		return "trips.txt";
	}
	
	@Override
	public Trip mapRow(Map<String, String> row) {
		
		TripRawDTO dto = objectMapper.convertValue(row, TripRawDTO.class);
		
		return tripMapper.toEntity(dto);
	}

}
