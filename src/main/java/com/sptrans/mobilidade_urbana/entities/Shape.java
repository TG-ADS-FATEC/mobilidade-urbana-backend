package com.sptrans.mobilidade_urbana.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

@Entity
@Table(name="shapes")
public class Shape {
	
	@Id
	private String shapeId;
	
	@OneToMany(mappedBy="shape", cascade=CascadeType.ALL, orphanRemoval=true)
	@OrderBy("id.sequence ASC")
	private List<ShapePoint> points = new ArrayList<>();
	
	@OneToMany(mappedBy="shape")
	private List<Trip> trips = new ArrayList<>();
	
	public Shape() {}
	
	
	public Shape(String shapeId, List<ShapePoint> points) {
		super();
		this.shapeId = shapeId;
		this.points = points;
	}



	public void addPoint(ShapePoint point) {
		point.setShape(this);
		points.add(point);
	}
	
	public void removePoint(ShapePoint point) {
		points.remove(point);
		point.setShape(null);
	}



	public String getShapeId() {
		return shapeId;
	}



	public void setShapeId(String shapeId) {
		this.shapeId = shapeId;
	}



	public List<ShapePoint> getPoints() {
		return points;
	}



	public void setPoints(List<ShapePoint> points) {
		this.points = points;
	}


	public List<Trip> getTrips() {
		return trips;
	}
	
	

}
