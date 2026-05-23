package com.sptrans.mobilidade_urbana.mappers;

import org.springframework.stereotype.Component;

import com.sptrans.mobilidade_urbana.dto.ShapePointDTO;
import com.sptrans.mobilidade_urbana.dto.ShapeRawDTO;
import com.sptrans.mobilidade_urbana.entities.ShapePoint;
import com.sptrans.mobilidade_urbana.entities.ShapePointId;

@Component
public class ShapePointMapper {
	
	public ShapePoint toEntity(ShapeRawDTO rawDto) {
		
		if(rawDto==null) {
			return null;
		}
		
		ShapePoint point = new ShapePoint();
		
		ShapePointId id = new ShapePointId(rawDto.getShapeId(), Integer.parseInt(rawDto.getSequence()));
		
		point.setShapePointId(id);
		
		point.setShapePointLatitude(Double.parseDouble(rawDto.getLatitude()));
		point.setShapePointLongitude(Double.parseDouble(rawDto.getLongitude()));
		
		if(rawDto.getDistanceTraveled()!= null && !rawDto.getDistanceTraveled().isBlank()) {
			point.setDistanceTraveled(Double.parseDouble(rawDto.getDistanceTraveled()));
		}
		
		return point;
	}
	
	public ShapePointDTO toDTO(ShapePoint entity) {
		
		if(entity==null) return null;
		
		return new ShapePointDTO(
				entity.getShapePointId().getSequence(),
				entity.getShapePointLatitude(),
				entity.getShapePointLongitude(),
				entity.getDistanceTraveled());
	}

}
