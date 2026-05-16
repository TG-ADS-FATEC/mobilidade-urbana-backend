package com.sptrans.mobilidade_urbana.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sptrans.mobilidade_urbana.dto.ShapeRowDTO;
import com.sptrans.mobilidade_urbana.entities.Shape;
import com.sptrans.mobilidade_urbana.entities.ShapePoint;
import com.sptrans.mobilidade_urbana.mappers.ShapePointMapper;

public class ShapeImportService {
	
	private final ShapePointMapper mapper;
	
	public ShapeImportService(ShapePointMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<Shape> importRows(List<ShapeRowDTO> rows){
		Map<Long, Shape> shapes = new HashMap<>();
		
		for(ShapeRowDTO row : rows) {
			
			Shape shape = shapes.computeIfAbsent(row.shapeId(), id -> {
				Shape s = new Shape();
				s.setShapeId(id);
				return s;
			});
			
			ShapePoint point = mapper.toEntity(row);
			
			shape.addPoint(point);
		}
		
		return new ArrayList<>(shapes.values());
	}

}
