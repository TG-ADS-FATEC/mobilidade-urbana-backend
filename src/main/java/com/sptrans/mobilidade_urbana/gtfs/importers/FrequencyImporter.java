package com.sptrans.mobilidade_urbana.gtfs.importers;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sptrans.mobilidade_urbana.dto.FrequencyRawDTO;
import com.sptrans.mobilidade_urbana.entities.Frequency;
import com.sptrans.mobilidade_urbana.gtfs.GTFSImporter;
import com.sptrans.mobilidade_urbana.mappers.FrequencyMapper;

@Component
public class FrequencyImporter implements GTFSImporter<Frequency> {
	
	private final ObjectMapper objectMapper;
	private final FrequencyMapper frequencyMapper;
	
	public FrequencyImporter(ObjectMapper objectMapper, FrequencyMapper frequencyMapper) {
		super();
		this.objectMapper = objectMapper;
		this.frequencyMapper = frequencyMapper;
	}
	
	@Override
	public String fileName() {
		return "frequencies.txt";
	}
	
	@Override
	public Frequency mapRow(Map<String, String> row) {
		
		FrequencyRawDTO dto = objectMapper.convertValue(row, FrequencyRawDTO.class);
		
		return frequencyMapper.toEntity(dto);
	}

}
