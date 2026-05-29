package com.sptrans.mobilidade_urbana.dto;

import com.sptrans.mobilidade_urbana.entities.DirectionId;

import io.swagger.v3.oas.annotations.media.Schema;

public class TripDTO {
	
	@Schema(description = "Id da viagem", example = "1012-10-0")
	private String tripId;
	@Schema(description = "Placa indicando o destino da viagem ", example = "Jd. Monte Belo")
	private String tripHeadsign;
	@Schema(description = "Direção da viagem do ônibus(ida ou volta)", example = "0")
	private DirectionId directionId;
	@Schema(description = "Id da linha", example = "1012-10")
	private String routeId;
	@Schema(description = "Id dos dias de serviço(calendário)", example = "USD")
	private String serviceId;
	@Schema(description = "Id do caminho percorrido pelo ônibus de uma linha", example = "84609")
	private String shapeId;
	
	public TripDTO() {}
	

	public TripDTO(String tripId, String tripHeadsign, DirectionId directionId, String routeId, String serviceId,
			String shapeId) {
		super();
		this.tripId = tripId;
		this.tripHeadsign = tripHeadsign;
		this.directionId = directionId;
		this.routeId = routeId;
		this.serviceId = serviceId;
		this.shapeId = shapeId;
	}


	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public String getTripHeadsign() {
		return tripHeadsign;
	}

	public void setTripHeadsign(String tripHeadsign) {
		this.tripHeadsign = tripHeadsign;
	}

	public DirectionId getDirectionId() {
		return directionId;
	}

	public void setDirectionId(DirectionId directionId) {
		this.directionId = directionId;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getShapeId() {
		return shapeId;
	}

	public void setShapeId(String shapeId) {
		this.shapeId = shapeId;
	}
	
	

}
