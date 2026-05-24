package com.sptrans.mobilidade_urbana.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.sptrans.mobilidade_urbana.dto.ShapeDTO;
import com.sptrans.mobilidade_urbana.dto.ShapePointDTO;
import com.sptrans.mobilidade_urbana.entities.Shape;

@Component
public class ShapeMapper {
	
	private ShapePointMapper shapePointMapper;
	
	public ShapeDTO toDTO(Shape entity) {
		
		if(entity==null) return null;
		
		List<ShapePointDTO> points = entity.getPoints()
				.stream()
				.map(shapePointMapper::toDTO)
				.toList();
		
		return new ShapeDTO(
				entity.getShapeId(),
				points);
	}

}
