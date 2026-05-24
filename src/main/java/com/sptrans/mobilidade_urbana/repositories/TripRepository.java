package com.sptrans.mobilidade_urbana.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sptrans.mobilidade_urbana.entities.Trip;

public interface TripRepository extends JpaRepository<Trip, String> {
	
	@Query("""
			SELECT trip
			FROM Trip trip
			JOIN trip.stopTimes stop_time
			JOIN stop_time.stop stop
			WHERE trip.route.id = :routeId
			ORDER BY trip.id, stop_time.stopSequence
			""")
	List<Trip> findTripsByRouteId(String routeId);

}
