package com.sptrans.mobilidade_urbana.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sptrans.mobilidade_urbana.dto.ShapeRowDTO;
import com.sptrans.mobilidade_urbana.entities.Shape;
import com.sptrans.mobilidade_urbana.entities.ShapePoint;
import com.sptrans.mobilidade_urbana.mappers.ShapePointMapper;

public class ShapeImportService {
	
	private final ShapePointMapper pointMapper;
	
	public ShapeImportService(ShapePointMapper pointMapper) {
		this.pointMapper = pointMapper;
	}
	
	public List<Shape> importRows(List<ShapeRowDTO> rows){
		Map<String, Shape> shapes = new HashMap<>();
		
		for(ShapeRowDTO row : rows) {
			
			Shape shape = shapes.computeIfAbsent(row.shapeId(), id -> {
				Shape s = new Shape();
				s.setShapeId(id);
				return s;
			});
			
			ShapePoint point = pointMapper.toEntity(row);
			
			shape.addPoint(point);
		}
		
		for(Shape shape : shapes.values()) {
			shape.getPoints().sort(Comparator.comparing(p -> p.getShapePointId().getSequence()));
		}
		
		return new ArrayList<>(shapes.values());
	}

}
