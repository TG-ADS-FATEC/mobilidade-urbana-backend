package com.sptrans.mobilidade_urbana.mappers;

import org.springframework.stereotype.Component;

import com.sptrans.mobilidade_urbana.dto.ShapeRowDTO;
import com.sptrans.mobilidade_urbana.entities.ShapePoint;
import com.sptrans.mobilidade_urbana.entities.ShapePointId;

@Component
public class ShapePointMapper {
	
	public ShapePoint toEntity(ShapeRowDTO dto) {
		ShapePoint point = new ShapePoint();
		
		point.setShapePointId(new ShapePointId(dto.shapeId(), dto.sequence()));
		
		point.setShapePointLatitude(dto.latitude());
		point.setShapePointLongitude(dto.longitude());
		point.setDistanceTraveled(dto.distanceTraveled());
		
		return point;
	}

}
