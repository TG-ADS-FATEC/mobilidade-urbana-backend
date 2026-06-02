package com.sptrans.mobilidade_urbana.dto;

import java.time.LocalDateTime;

import com.sptrans.mobilidade_urbana.entities.RouteType;

import io.swagger.v3.oas.annotations.media.Schema;


public class FavoriteRouteDTO {
	
	@Schema(description = "Id do favorito gerado automaticamente", example = "1")
	private Long favoriteRouteId;
	@Schema(description = "Nome curto da rota favoritada", example = "1012-10")
	private String favoriteRouteShortName;
	@Schema(description = "Nome longo da rota favoritada", example = "Term. Jd. Britania - Jd. Monte Belo")
	private String favoriteRouteLongName;
	@Schema(description = "Tipo de transporte da rota favoritada", example = "BUS")
	private RouteType routeType;
	@Schema(description = "Data de criação do favorito", example = "2026-05-07T22:09:49.22619787")
	private LocalDateTime createdAt;
	@Schema(description = "Id da linha favoritada", requiredMode = Schema.RequiredMode.REQUIRED, example = "1012-10")
	private String routeId;
	
	public FavoriteRouteDTO() {}

	public FavoriteRouteDTO(Long favoriteRouteId, String favoriteRouteShortName, String favoriteRouteLongName,
			RouteType routeType, LocalDateTime createdAt, String routeId) {
		super();
		this.favoriteRouteId = favoriteRouteId;
		this.favoriteRouteShortName = favoriteRouteShortName;
		this.favoriteRouteLongName = favoriteRouteLongName;
		this.routeType = routeType;
		this.createdAt = createdAt;
		this.routeId = routeId;
	}

	public Long getFavoriteRouteId() {
		return favoriteRouteId;
	}

	public void setFavoriteRouteId(Long favoriteRouteId) {
		this.favoriteRouteId = favoriteRouteId;
	}

	public String getFavoriteRouteShortName() {
		return favoriteRouteShortName;
	}

	public void setFavoriteRouteShortName(String favoriteRouteShortName) {
		this.favoriteRouteShortName = favoriteRouteShortName;
	}

	public String getFavoriteRouteLongName() {
		return favoriteRouteLongName;
	}

	public void setFavoriteRouteLongName(String favoriteRouteLongName) {
		this.favoriteRouteLongName = favoriteRouteLongName;
	}

	public RouteType getRouteType() {
		return routeType;
	}

	public void setRouteType(RouteType routeType) {
		this.routeType = routeType;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
	
	
}
