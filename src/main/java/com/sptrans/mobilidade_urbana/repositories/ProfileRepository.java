package com.sptrans.mobilidade_urbana.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sptrans.mobilidade_urbana.entities.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
	
	Optional <Profile> findByDevice_DeviceToken(UUID deviceToken);

}
