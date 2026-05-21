package com.sptrans.mobilidade_urbana.dto;

import com.sptrans.mobilidade_urbana.entities.RouteType;

public class RouteDTO {
	
	private String routeId;
	private String routeShortName;
	private String routeLongName;
	private RouteType routeType;
	private String routeColor;
	private String routeTextColor;
	private String agencyId;
	
	public RouteDTO() {}

	public RouteDTO(String routeId, String routeShortName, String routeLongName, RouteType routeType, String routeColor,
			String routeTextColor, String agencyId) {
		super();
		this.routeId = routeId;
		this.routeShortName = routeShortName;
		this.routeLongName = routeLongName;
		this.routeType = routeType;
		this.routeColor = routeColor;
		this.routeTextColor = routeTextColor;
		this.agencyId = agencyId;
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

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}
	

}
