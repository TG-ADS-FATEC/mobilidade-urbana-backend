package com.sptrans.mobilidade_urbana.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.entities.Preference;

public interface PreferenceRepository extends JpaRepository<Preference, UUID> {

	@Query("""
			select p from Preference p
			join p.profile pr
			where pr.device.deviceId = :deviceId
			""")
	Optional<Preference> findByDeviceId(UUID deviceId);
}
