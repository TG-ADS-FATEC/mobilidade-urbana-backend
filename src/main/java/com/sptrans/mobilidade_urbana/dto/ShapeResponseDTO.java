package com.sptrans.mobilidade_urbana.dto;

import java.util.List;

public record ShapeResponseDTO(
		Long shapeId,
		List<ShapeRowDTO> points) {

}
