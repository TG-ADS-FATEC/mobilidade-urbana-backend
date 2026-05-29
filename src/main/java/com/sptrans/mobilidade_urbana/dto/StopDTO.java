package com.sptrans.mobilidade_urbana.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class StopDTO {
	
	@Schema(description = "Id da parada", example = "19073")
	private String stopId;
	@Schema(description = "Nome da parada", example = "Rod. Arão Sahm, 25020")
	private String stopName;
	@Schema(description = "Descrição da parada", example = "Ref.: Av. Rubi")
	private String stopDescription;
	@Schema(description = "Latitude da parada", example = "-23.373502")
	private Double stopLatitude;
	@Schema(description = "Longitude da parada", example = "-46.575335")
	private Double stopLongitude;
	
	public StopDTO() {}
	
	public StopDTO(String stopId, String stopName, String stopDescription, Double stopLatitude, Double stopLongitude) {
		super();
		this.stopId = stopId;
		this.stopName = stopName;
		this.stopDescription = stopDescription;
		this.stopLatitude = stopLatitude;
		this.stopLongitude = stopLongitude;
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

	public String getStopDescription() {
		return stopDescription;
	}

	public void setStopDescription(String stopDescription) {
		this.stopDescription = stopDescription;
	}

	public Double getStopLatitude() {
		return stopLatitude;
	}

	public void setStopLatitude(Double stopLatitude) {
		this.stopLatitude = stopLatitude;
	}

	public Double getStopLongitude() {
		return stopLongitude;
	}

	public void setStopLongitude(Double stopLongitude) {
		this.stopLongitude = stopLongitude;
	}

}
