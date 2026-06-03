package com.sptrans.mobilidade_urbana.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sptrans.mobilidade_urbana.entities.Preference;

public interface PreferenceRepository extends JpaRepository<Preference, UUID> {

	/*@Query("""
			SELECT preference FROM Preference preference
			JOIN preference.profile profile
			WHERE profile.device.deviceId = :deviceId
			""")*/
	Optional<Preference> findByProfileDeviceDeviceId(UUID deviceId);
}
