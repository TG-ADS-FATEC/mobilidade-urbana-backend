package com.sptrans.mobilidade_urbana.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sptrans.mobilidade_urbana.entities.FavoriteTrip;
import com.sptrans.mobilidade_urbana.entities.Profile;

public interface FavoriteTripRepository extends JpaRepository<FavoriteTrip, Long> {

	List<FavoriteTrip> findByProfile(Profile profile);

}
