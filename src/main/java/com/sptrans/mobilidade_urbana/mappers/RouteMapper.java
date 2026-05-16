package com.sptrans.mobilidade_urbana.mappers;

import org.springframework.stereotype.Component;

import com.sptrans.mobilidade_urbana.dto.RouteDTO;
import com.sptrans.mobilidade_urbana.entities.Agency;
import com.sptrans.mobilidade_urbana.entities.Route;

import jakarta.persistence.EntityManager;

@Component
public class RouteMapper {
	
	private final EntityManager entityManager;

	public RouteMapper(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}
	
	public Route toEntity(RouteDTO dto) {
		if(dto==null) {
			return null;
		}
		
		Route route = new Route();
		route.setRouteId(dto.getRouteId());
		route.setRouteShortName(dto.getRouteShortName());
		route.setRouteLongName(dto.getRouteLongName());
		route.setRouteType(dto.getRouteType());
		route.setRouteColor(dto.getRouteColor());
		route.setRouteTextColor(dto.getRouteTextColor());
		route.setAgency(entityManager.getReference(Agency.class, dto.getAgencyId()));
		
		return route;
	}

}
