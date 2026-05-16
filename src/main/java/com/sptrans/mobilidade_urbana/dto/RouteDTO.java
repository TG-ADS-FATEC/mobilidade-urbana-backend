package com.sptrans.mobilidade_urbana.dto;

import com.sptrans.mobilidade_urbana.entities.Route;
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
	
	public RouteDTO(Route entity) {
		routeId = entity.getRouteId();
		routeShortName = entity.getRouteShortName();
		routeLongName = entity.getRouteLongName();
		routeType = entity.getRouteType();
		routeColor = entity.getRouteColor();
		routeTextColor = entity.getRouteTextColor();
		agencyId = entity.getAgency().getAgencyId();
	}

	public String getRouteId() {
		return routeId;
	}

	public String getRouteShortName() {
		return routeShortName;
	}

	public String getRouteLongName() {
		return routeLongName;
	}

	public RouteType getRouteType() {
		return routeType;
	}

	public String getRouteColor() {
		return routeColor;
	}

	public String getRouteTextColor() {
		return routeTextColor;
	}

	public String getAgencyId() {
		return agencyId;
	}
	
	

}
