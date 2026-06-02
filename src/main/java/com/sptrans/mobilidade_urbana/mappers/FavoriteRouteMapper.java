package com.sptrans.mobilidade_urbana.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.sptrans.mobilidade_urbana.dto.FavoriteRouteDTO;
import com.sptrans.mobilidade_urbana.entities.FavoriteRoute;
import com.sptrans.mobilidade_urbana.entities.Profile;
import com.sptrans.mobilidade_urbana.entities.Route;

import jakarta.persistence.EntityManager;

@Component
public class FavoriteRouteMapper {
	
	private final EntityManager entityManager;
	
	public FavoriteRouteMapper(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}
	
	public FavoriteRoute toEntity(FavoriteRouteDTO dto, UUID profileId) {
		
		FavoriteRoute favoriteRoute = new FavoriteRoute();
		
		favoriteRoute.setFavoriteRouteShortName(dto.getFavoriteRouteShortName());
		favoriteRoute.setFavoriteRouteLongName(dto.getFavoriteRouteLongName());
		favoriteRoute.setRouteType(dto.getRouteType());
		favoriteRoute.setProfile(entityManager.getReference(Profile.class, profileId));
		favoriteRoute.setRoute(entityManager.getReference(Route.class,dto.getRouteId()));
		
		return favoriteRoute;
	}

	public FavoriteRouteDTO toDTO(FavoriteRoute entity) {
		
		if(entity==null) return null;
		
		String favoriteRouteShortName = null; 
		
		if(entity.getRoute() != null && entity.getRoute().getRouteShortName() != null) {
			favoriteRouteShortName = entity.getRoute().getRouteShortName();
		}
		
		String favoriteRouteLongName = null; 
		
		if(entity.getRoute() != null && entity.getRoute().getRouteLongName() != null) {
			favoriteRouteLongName = entity.getRoute().getRouteLongName();
		}
		
		
		return new FavoriteRouteDTO(
				entity.getFavoriteRouteId(),
				favoriteRouteShortName,
				favoriteRouteLongName,
				entity.getRoute().getRouteType(),
				entity.getCreatedAt(),
				entity.getRoute().getRouteId());
	}
	
	public List<FavoriteRouteDTO> toDTOList(List<FavoriteRoute> entities) {
		if(entities == null || entities.isEmpty()) {
			return new ArrayList<>();
		}
		
		return entities.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}
	
	public Page<FavoriteRouteDTO> toDTOPage(Page<FavoriteRoute> page) {
		return page.map(this::toDTO);
	}

}
