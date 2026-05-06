package com.sptrans.mobilidade_urbana.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sptrans.mobilidade_urbana.entities.Profile;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
	
	@Modifying
	@Query("delete from Profile p where p.device.deviceId = :deviceId")
	void deleteByDeviceId(UUID deviceId);
	
	Optional <Profile> findByDevice_DeviceId(UUID deviceId);

}
