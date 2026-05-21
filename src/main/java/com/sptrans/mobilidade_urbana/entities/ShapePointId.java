package com.sptrans.mobilidade_urbana.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class ShapePointId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String shapeId;
	
	private Integer sequence;
	
	public ShapePointId() {}

	public ShapePointId(String shapeId, Integer sequence) {
		super();
		this.shapeId = shapeId;
		this.sequence = sequence;
	}

	public String getShapeId() {
		return shapeId;
	}

	public void setShapeId(String shapeId) {
		this.shapeId = shapeId;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	@Override
	public int hashCode() {
		return Objects.hash(sequence, shapeId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShapePointId other = (ShapePointId) obj;
		return Objects.equals(sequence, other.sequence) && Objects.equals(shapeId, other.shapeId);
	}
	
	

}
