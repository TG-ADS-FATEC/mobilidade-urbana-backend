package com.sptrans.mobilidade_urbana.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name="shape_point")
public class ShapePoint {
	
	@EmbeddedId
	private ShapePointId shapePointId;
	
	@MapsId("shapeId")
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name = "shape_id", nullable=false)
	private Shape shape;
	
	private Double shapePointLatitude;
	
	private Double shapePointLongitude;
	
	private Double distanceTraveled;
	
	public ShapePoint() {}

	public ShapePoint(ShapePointId shapePointId, Shape shape, Double shapePointLatitude, Double shapePointLongitude,
			Double distanceTraveled) {
		super();
		this.shapePointId = shapePointId;
		this.shape = shape;
		this.shapePointLatitude = shapePointLatitude;
		this.shapePointLongitude = shapePointLongitude;
		this.distanceTraveled = distanceTraveled;
	}

	public ShapePointId getShapePointId() {
		return shapePointId;
	}

	public void setShapePointId(ShapePointId shapePointId) {
		this.shapePointId = shapePointId;
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public Double getShapePointLatitude() {
		return shapePointLatitude;
	}

	public void setShapePointLatitude(Double shapePointLatitude) {
		this.shapePointLatitude = shapePointLatitude;
	}

	public Double getShapePointLongitude() {
		return shapePointLongitude;
	}

	public void setShapePointLongitude(Double shapePointLongitude) {
		this.shapePointLongitude = shapePointLongitude;
	}

	public Double getDistanceTraveled() {
		return distanceTraveled;
	}

	public void setDistanceTraveled(Double distanceTraveled) {
		this.distanceTraveled = distanceTraveled;
	}
	
	

}
