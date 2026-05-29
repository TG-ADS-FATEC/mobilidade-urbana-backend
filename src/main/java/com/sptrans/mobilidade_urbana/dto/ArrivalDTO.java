package com.sptrans.mobilidade_urbana.dto;

import com.sptrans.mobilidade_urbana.entities.DirectionId;
import com.sptrans.mobilidade_urbana.gtfs.GTFSTime;

import io.swagger.v3.oas.annotations.media.Schema;

public class ArrivalDTO {
	
	@Schema(description = "Id da linha", example = "1012-10")
	private String routeId;
	@Schema(description = "Id da viagem", example = "1012-10-0")
	private String tripId;
	@Schema(description = "Id da parada", example = "301790")
	private String stopId;
	@Schema(description = "Nome da parada", example = "Terminal Jardim Britânia")
	private String stopName;
	@Schema(description = "Direção da viagem do ônibus(ida ou volta)", example = "0")
	private DirectionId directionId;
	@Schema(description = "Tempo de chegada na parada", example = "07:00:00")
	private GTFSTime arrivalTime;
	@Schema(description = "Sequência das paradas", example = "1")
	private Integer stopSequence;
	@Schema(description = "Chegada do ônibus em minutos", example = "5")
	private Integer arrivalInMinutes;
	
	
	public ArrivalDTO() {}


	public ArrivalDTO(String routeId, String tripId, String stopId, String stopName, DirectionId directionId,
			GTFSTime arrivalTime, Integer stopSequence, Integer arrivalInMinutes) {
		super();
		this.routeId = routeId;
		this.tripId = tripId;
		this.stopId = stopId;
		this.stopName = stopName;
		this.directionId = directionId;
		this.arrivalTime = arrivalTime;
		this.stopSequence = stopSequence;
		this.arrivalInMinutes = arrivalInMinutes;
	}


	public String getRouteId() {
		return routeId;
	}


	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}


	public String getTripId() {
		return tripId;
	}


	public void setTripId(String tripId) {
		this.tripId = tripId;
	}


	public String getStopId() {
		return stopId;
	}


	public void setStopId(String stopId) {
		this.stopId = stopId;
	}


	public String getStopName() {
		return stopName;
	}


	public void setStopName(String stopName) {
		this.stopName = stopName;
	}


	public DirectionId getDirectionId() {
		return directionId;
	}


	public void setDirectionId(DirectionId directionId) {
		this.directionId = directionId;
	}


	public GTFSTime getArrivalTime() {
		return arrivalTime;
	}


	public void setArrivalTime(GTFSTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}


	public Integer getStopSequence() {
		return stopSequence;
	}


	public void setStopSequence(Integer stopSequence) {
		this.stopSequence = stopSequence;
	}


	public Integer getArrivalInMinutes() {
		return arrivalInMinutes;
	}


	public void setArrivalInMinutes(Integer arrivalInMinutes) {
		this.arrivalInMinutes = arrivalInMinutes;
	}
	

}
