package com.sptrans.mobilidade_urbana.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.sptrans.mobilidade_urbana.dto.FavoriteTripDTO;
import com.sptrans.mobilidade_urbana.entities.FavoriteTrip;
import com.sptrans.mobilidade_urbana.entities.Profile;

import jakarta.persistence.EntityManager;

@Component
public class FavoriteTripMapper {
	
private final EntityManager entityManager;
	
	public FavoriteTripMapper(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}
	
	public FavoriteTrip toEntity(FavoriteTripDTO dto, UUID profileId) {
		
		FavoriteTrip favoriteTrip = new FavoriteTrip();
		
		favoriteTrip.setFavoriteTripName(dto.getFavoriteTripName());
		favoriteTrip.setDestinationLatitude(dto.getDestinationLatitude());
		favoriteTrip.setDestinationLongitude(dto.getDestinationLongitude());
		favoriteTrip.setProfile(entityManager.getReference(Profile.class, profileId));
		
		return favoriteTrip;
	}
	
	public FavoriteTripDTO toDTO(FavoriteTrip entity) {
		if(entity==null) return null;
		
		return new FavoriteTripDTO(
				entity.getFavoriteTripId(),
				entity.getFavoriteTripName(),
				entity.getDestinationLatitude(),
				entity.getDestinationLongitude(),
				entity.getCreatedAt());
	}
	
	public List<FavoriteTripDTO> toDTOList(List<FavoriteTrip> entities) {
		if(entities == null || entities.isEmpty()) {
			return new ArrayList<>();
		}
		
		return entities.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}
	
	public Page<FavoriteTripDTO> toDTOPage(Page<FavoriteTrip> page) {
		return page.map(this::toDTO);
	}

}
