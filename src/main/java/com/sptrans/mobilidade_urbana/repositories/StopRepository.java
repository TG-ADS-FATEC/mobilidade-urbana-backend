package com.sptrans.mobilidade_urbana.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sptrans.mobilidade_urbana.entities.Stop;

public interface StopRepository extends JpaRepository<Stop, String> {
	
	@Query
	("""
			SELECT DISTINCT stop
			FROM Stop stop
			JOIN stop.stopTimes stop_time
			JOIN stop_time.trip trip
			JOIN trip.route route
			WHERE route.id = :routeId 
			""")
	Page<Stop> findStopsByRouteId(@Param("routeId") String routeId, Pageable pageable);
	
	@Query(value = """
			SELECT * FROM stop stop 
			WHERE ST_DistanceSphere(ST_MakePoint(stop.stop_longitude, stop.stop_latitude), ST_MakePoint(:longitude, :latitude)) <= :radius
			ORDER BY ST_DistanceSphere(ST_MakePoint(stop.stop_longitude, stop.stop_latitude), ST_MakePoint(:longitude, :latitude))""",
			countQuery = """
					SELECT count(*) FROM stop stop
					WHERE ST_DistanceSphere(ST_MakePoint(stop.stop_longitude, stop.stop_latitude), ST_MakePoint(:longitude, :latitude)) <= :radius
					""",
			nativeQuery = true 
			)
	Page<Stop> findNearbyStops(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("radius") double radius, Pageable pageable);

}
