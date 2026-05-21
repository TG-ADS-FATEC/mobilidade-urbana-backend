package com.sptrans.mobilidade_urbana.mappers;

import org.springframework.stereotype.Component;

import com.sptrans.mobilidade_urbana.dto.RouteRawDTO;
import com.sptrans.mobilidade_urbana.entities.Agency;
import com.sptrans.mobilidade_urbana.entities.Route;
import com.sptrans.mobilidade_urbana.entities.RouteType;

import jakarta.persistence.EntityManager;

@Component
public class RouteMapper {
	
	private final EntityManager entityManager;

	public RouteMapper(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}
	
	public Route toEntity(RouteRawDTO rawDto) {
		if(rawDto==null) {
			return null;
		}
		
		Route route = new Route();
		route.setRouteId(rawDto.getRouteId());
		route.setRouteShortName(rawDto.getRouteShortName());
		route.setRouteLongName(rawDto.getRouteLongName());
		route.setRouteType(rawDto.getRouteType() == null || rawDto.getRouteType().isBlank() ? null : RouteType.fromCode(Integer.parseInt(rawDto.getRouteType())));
		route.setRouteColor(rawDto.getRouteColor());
		route.setRouteTextColor(rawDto.getRouteTextColor());
		route.setAgency(entityManager.getReference(Agency.class, rawDto.getAgencyId()));
		
		return route;
	}

}
