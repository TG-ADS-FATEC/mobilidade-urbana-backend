package com.sptrans.mobilidade_urbana.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sptrans.mobilidade_urbana.dto.ShapeRawDTO;
import com.sptrans.mobilidade_urbana.entities.Shape;
import com.sptrans.mobilidade_urbana.entities.ShapePoint;
import com.sptrans.mobilidade_urbana.mappers.ShapePointMapper;
import com.sptrans.mobilidade_urbana.repositories.ShapeRepository;

import jakarta.transaction.Transactional;

@Service
public class ShapeService {
	
	private final ShapePointMapper shapePointMapper;
	private final ShapeRepository shapeRepository;
	
	public ShapeService(ShapePointMapper shapePointMapper, ShapeRepository shapeRepository) {
		super();
		this.shapePointMapper = shapePointMapper;
		this.shapeRepository = shapeRepository;
	}
	
	@Transactional
	public void importShapes(List<ShapeRawDTO> rows) {
		
		Map<String, Shape> shapesMap = new HashMap<>();
		
		for(ShapeRawDTO dto: rows) {
			Shape shape = shapesMap.computeIfAbsent(dto.getShapeId(), id -> new Shape(id, new ArrayList<>()));
			
			ShapePoint point = shapePointMapper.toEntity(dto);
			
			shape.addPoint(point);
		}
		
		//Se o import quebrar, utilizar este código e na entidade Shape retirar o @OrderBy("shapePointId.sequence ASC")
		
		/*for(Shape shape : shapesMap.values()) {
			shape.getPoints().sort(Comparator.comparing(points -> points.getShapePointId().getSequence()));
		}*/
		
		shapeRepository.saveAll(shapesMap.values());
		shapeRepository.flush();
		
	}
	
	

}
