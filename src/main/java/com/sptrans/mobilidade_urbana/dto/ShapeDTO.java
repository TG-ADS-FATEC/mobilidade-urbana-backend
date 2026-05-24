package com.sptrans.mobilidade_urbana.dto;

import java.util.List;

public class ShapeDTO {
	
	private String shapeId;
	
	private List<ShapePointDTO> points;

	public ShapeDTO(String shapeId, List<ShapePointDTO> points) {
		super();
		this.shapeId = shapeId;
		this.points = points;
	}

	public String getShapeId() {
		return shapeId;
	}

	public void setShapeId(String shapeId) {
		this.shapeId = shapeId;
	}

	public List<ShapePointDTO> getPoints() {
		return points;
	}

	public void setPoints(List<ShapePointDTO> points) {
		this.points = points;
	}
	
	

}
