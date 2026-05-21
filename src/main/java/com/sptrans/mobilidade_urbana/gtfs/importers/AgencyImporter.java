package com.sptrans.mobilidade_urbana.gtfs.importers;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sptrans.mobilidade_urbana.dto.AgencyRawDTO;
import com.sptrans.mobilidade_urbana.entities.Agency;
import com.sptrans.mobilidade_urbana.gtfs.GTFSImporter;
import com.sptrans.mobilidade_urbana.mappers.AgencyMapper;

@Component
public class AgencyImporter implements GTFSImporter<Agency> {
	
	private final ObjectMapper objectMapper;
	private final AgencyMapper agencyMapper;
	
	public AgencyImporter(ObjectMapper objectMapper, AgencyMapper agencyMapper) {
		super();
		this.objectMapper = objectMapper;
		this.agencyMapper = agencyMapper;
	}
	
	@Override
	public String fileName() {
		return "agency.txt";
	}
	
	@Override
	public Agency mapRow(Map<String, String> row) {
		
		AgencyRawDTO dto = objectMapper.convertValue(row, AgencyRawDTO.class);
		
		return agencyMapper.toEntity(dto);
	}

}
