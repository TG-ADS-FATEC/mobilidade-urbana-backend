package com.sptrans.mobilidade_urbana.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sptrans.mobilidade_urbana.entities.FavoriteRoute;
import com.sptrans.mobilidade_urbana.entities.Profile;

public interface FavoriteRouteRepository extends JpaRepository<FavoriteRoute, Long> {

	List<FavoriteRoute> findByProfile(Profile profile);

}
