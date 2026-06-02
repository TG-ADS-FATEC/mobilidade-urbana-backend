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
@Table(name="favorite_trips")
public class FavoriteTrip {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long favoriteTripId;
	private String favoriteTripName;
	private Double destinationLatitude;
	private Double destinationLongitude;
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private LocalDateTime createdAt;
	
	@ManyToOne
	@JoinColumn(name="profile_id")
	private Profile profile;
	
	public FavoriteTrip() {}

	public FavoriteTrip(Long favoriteTripId, String favoriteTripName, Double destinationLatitude,
			Double destinationLongitude, LocalDateTime createdAt, Profile profile) {
		super();
		this.favoriteTripId = favoriteTripId;
		this.favoriteTripName = favoriteTripName;
		this.destinationLatitude = destinationLatitude;
		this.destinationLongitude = destinationLongitude;
		this.createdAt = createdAt;
		this.profile = profile;
	}

	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
	}

	public Long getFavoriteTripId() {
		return favoriteTripId;
	}

	public void setFavoriteTripId(Long favoriteTripId) {
		this.favoriteTripId = favoriteTripId;
	}

	public String getFavoriteTripName() {
		return favoriteTripName;
	}

	public void setFavoriteTripName(String favoriteTripName) {
		this.favoriteTripName = favoriteTripName;
	}

	public Double getDestinationLatitude() {
		return destinationLatitude;
	}

	public void setDestinationLatitude(Double destinationLatitude) {
		this.destinationLatitude = destinationLatitude;
	}

	public Double getDestinationLongitude() {
		return destinationLongitude;
	}

	public void setDestinationLongitude(Double destinationLongitude) {
		this.destinationLongitude = destinationLongitude;
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

}
