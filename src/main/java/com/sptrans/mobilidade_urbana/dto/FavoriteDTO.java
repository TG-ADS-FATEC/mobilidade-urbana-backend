package com.sptrans.mobilidade_urbana.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.sptrans.mobilidade_urbana.entities.Favorite;

import jakarta.validation.constraints.NotBlank;


public class FavoriteDTO {
	
	private Long favoriteId;
	@NotBlank(message="O nome não pode ser vazio")
	private String favoriteName;
	private LocalDateTime createdAt;
	
	public FavoriteDTO() {}

	public FavoriteDTO(Long favoriteId, String favoriteName, LocalDateTime createdAt, UUID profileId) {
		super();
		this.favoriteId = favoriteId;
		this.favoriteName = favoriteName;
		this.createdAt = createdAt;
	}
	
	public FavoriteDTO(Favorite entity) {
		favoriteId = entity.getFavoriteId();
		favoriteName = entity.getFavoriteName();
		createdAt = entity.getCreatedAt();
	}

	public Long getFavoriteId() {
		return favoriteId;
	}

	public String getFavoriteName() {
		return favoriteName;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
}
