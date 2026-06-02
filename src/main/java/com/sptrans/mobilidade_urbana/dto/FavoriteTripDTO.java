package com.sptrans.mobilidade_urbana.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class FavoriteTripDTO {
	
	@Schema(description = "Id do favorito gerado automaticamente", example = "1")
	private Long favoriteTripId;
	@Schema(description = "Nome da viagem favorita", example = "Casa")
	@NotBlank(message = "O nome não pode ser vazio")
	private String favoriteTripName;
	@Schema(description = "Latitude do destino", example = "-23.563914")
	private Double destinationLatitude;
	@Schema(description = "Longitude do destino", example = "-46.681729")
	private Double destinationLongitude;
	@Schema(description = "Data de criação do favorito", example = "2026-05-07T22:09:49.22619787")
	private LocalDateTime createdAt;
	
	public FavoriteTripDTO() {}

	public FavoriteTripDTO(Long favoriteTripId, String favoriteTripName, Double destinationLatitude,
			Double destinationLongitude, LocalDateTime createdAt) {
		super();
		this.favoriteTripId = favoriteTripId;
		this.favoriteTripName = favoriteTripName;
		this.destinationLatitude = destinationLatitude;
		this.destinationLongitude = destinationLongitude;
		this.createdAt = createdAt;
	}

	public Long getFavoriteTripId() {
		return favoriteTripId;
	}

	public void setFavoriteTripId(Long favoriteTripId) {
		this.favoriteTripId = favoriteTripId;
	}

	public String getFavoriteTripName() {
		return favoriteTripName;
	}

	public void setFavoriteTripName(String favoriteTripName) {
		this.favoriteTripName = favoriteTripName;
	}

	public Double getDestinationLatitude() {
		return destinationLatitude;
	}

	public void setDestinationLatitude(Double destinationLatitude) {
		this.destinationLatitude = destinationLatitude;
	}

	public Double getDestinationLongitude() {
		return destinationLongitude;
	}

	public void setDestinationLongitude(Double destinationLongitude) {
		this.destinationLongitude = destinationLongitude;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
