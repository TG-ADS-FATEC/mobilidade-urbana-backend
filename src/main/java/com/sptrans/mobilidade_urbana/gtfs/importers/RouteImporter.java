package com.sptrans.mobilidade_urbana.gtfs.importers;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sptrans.mobilidade_urbana.dto.RouteRawDTO;
import com.sptrans.mobilidade_urbana.entities.Route;
import com.sptrans.mobilidade_urbana.gtfs.GTFSImporter;
import com.sptrans.mobilidade_urbana.mappers.RouteMapper;

@Component
public class RouteImporter implements GTFSImporter<Route> {
	
	private final ObjectMapper objectMapper;
	private final RouteMapper routeMapper;
	
	public RouteImporter(ObjectMapper objectMapper, RouteMapper routeMapper) {
		super();
		this.objectMapper = objectMapper;
		this.routeMapper = routeMapper;
	}

	@Override
	public String fileName() {
		return "routes.txt";
	}

	@Override
	public Route mapRow(Map<String, String> row) {
		RouteRawDTO dto = objectMapper.convertValue(row, RouteRawDTO.class);
		
		return routeMapper.toEntity(dto);
	}

}
