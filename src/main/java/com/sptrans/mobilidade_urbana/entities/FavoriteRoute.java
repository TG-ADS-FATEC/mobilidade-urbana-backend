package com.sptrans.mobilidade_urbana.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name="favorite_routes")
public class FavoriteRoute {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long favoriteRouteId;
	private String favoriteRouteShortName;
	private String favoriteRouteLongName;
	private RouteType routeType;
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private LocalDateTime createdAt;
	
	@ManyToOne
	@JoinColumn(name="profile_id")
	private Profile profile;
	
	@ManyToOne
	@JoinColumn(name="route_id")
	private Route route;
	
	public FavoriteRoute() {}
	
	public FavoriteRoute(Long favoriteRouteId, String favoriteRouteShortName, String favoriteRouteLongName,
			RouteType routeType, LocalDateTime createdAt, Profile profile, Route route) {
		super();
		this.favoriteRouteId = favoriteRouteId;
		this.favoriteRouteShortName = favoriteRouteShortName;
		this.favoriteRouteLongName = favoriteRouteLongName;
		this.routeType = routeType;
		this.createdAt = createdAt;
		this.profile = profile;
		this.route = route;
	}

	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
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

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}
	
	
}
