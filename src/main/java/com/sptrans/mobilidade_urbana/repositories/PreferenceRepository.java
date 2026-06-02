package com.sptrans.mobilidade_urbana.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sptrans.mobilidade_urbana.entities.Preference;

public interface PreferenceRepository extends JpaRepository<Preference, UUID> {

	Optional<Preference> findByDevice_DeviceId(UUID deviceId);
}
