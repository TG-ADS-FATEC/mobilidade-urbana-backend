package com.sptrans.mobilidade_urbana.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sptrans.mobilidade_urbana.entities.Favorite;
import com.sptrans.mobilidade_urbana.entities.Profile;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

	List<Favorite> findByProfile(Profile profile);

}
