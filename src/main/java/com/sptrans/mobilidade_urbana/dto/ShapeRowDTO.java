package com.sptrans.mobilidade_urbana.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ShapeRowDTO(
		
		@JsonProperty("shape_id")
		String shapeId,
		
		@JsonProperty("shape_pt_lat")
		Double latitude,
		
		@JsonProperty("shape_pt_lon")
		Double longitude,
		
		@JsonProperty("shape_pt_lon")
		Integer sequence,
		
		@JsonProperty("shape_dist_traveled")
		Double distanceTraveled
		
		) {}
