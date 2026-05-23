package com.sptrans.mobilidade_urbana.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sptrans.mobilidade_urbana.entities.Route;

public interface RouteRepository extends JpaRepository<Route, String> {
	
	@Query("""
			SELECT r
			FROM Route r
			WHERE
				LOWER(r.routeShortName) = LOWER(:query)
				
				OR LOWER(r.routeShortName) LIKE LOWER(CONCAT(:query, '%'))
				
				OR LOWER(r.routeLongName) LIKE LOWER(CONCAT('%', :query, '%'))
			ORDER BY
				CASE
					WHEN LOWER(r.routeShortName) = LOWER(:query) THEN 1
					WHEN LOWER(r.routeShortName) LIKE LOWER(CONCAT(:query, '%')) THEN 2
					ELSE 3
				END
				
			""")
	Page<Route> search(@Param("query") String query, Pageable pageable);
	
	@Query(value="""
			SELECT DISTINCT route
			FROM Route route
			JOIN route.trips trip
			JOIN trip.stopTimes stop_time
			WHERE stop_time.stop.id = :stopId
			""",
			countQuery = """
					SELECT COUNT(DISTINCT route)
					FROM Route route
					JOIN route.trips trip
					JOIN trip.stopTimes stop_time
					WHERE stop_time.stop.id = :stopId
					""")
	Page<Route> findRoutesByStopId(@Param("stopId") String stopId, Pageable pageable);

}
