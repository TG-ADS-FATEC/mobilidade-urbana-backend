package com.sptrans.mobilidade_urbana.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Route {
	
	@Id
	private String routeId;
	private String routeShortName;
	private String routeLongName;
	@Enumerated(EnumType.STRING)
	private RouteType routeType;
	private String routeColor;
	private String routeTextColor;
	
	@ManyToOne
	@JoinColumn(name="agency_id")
	private Agency agency;
	
	@OneToMany(mappedBy="route")
	private List<Trip> trips;
	
	public Route() {}

	public Route(String routeId, String routeShortName, String routeLongName, RouteType routeType, String routeColor,
			String routeTextColor, Agency agency) {
		super();
		this.routeId = routeId;
		this.routeShortName = routeShortName;
		this.routeLongName = routeLongName;
		this.routeType = routeType;
		this.routeColor = routeColor;
		this.routeTextColor = routeTextColor;
		this.agency = agency;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getRouteShortName() {
		return routeShortName;
	}

	public void setRouteShortName(String routeShortName) {
		this.routeShortName = routeShortName;
	}

	public String getRouteLongName() {
		return routeLongName;
	}

	public void setRouteLongName(String routeLongName) {
		this.routeLongName = routeLongName;
	}

	public RouteType getRouteType() {
		return routeType;
	}

	public void setRouteType(RouteType routeType) {
		this.routeType = routeType;
	}

	public String getRouteColor() {
		return routeColor;
	}

	public void setRouteColor(String routeColor) {
		this.routeColor = routeColor;
	}

	public String getRouteTextColor() {
		return routeTextColor;
	}

	public void setRouteTextColor(String routeTextColor) {
		this.routeTextColor = routeTextColor;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public List<Trip> getTrips() {
		return trips;
	}
	

}
