package com.sptrans.mobilidade_urbana.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sptrans.mobilidade_urbana.entities.StopTime;

public interface StopTimeRepository extends JpaRepository<StopTime, Long> {
	
	@Query("""
			SELECT stop_time
			FROM StopTime stop_time
			JOIN FETCH stop_time.trip trip
			JOIN FETCH trip.route
			JOIN FETCH stop_time.stop
			WHERE stop_time.stop.stopId = :stopId
			AND stop_time.arrivalTime.secondsFromMidnight BETWEEN :currentSeconds AND :maxSeconds
			ORDER BY stop_time.arrivalTime.secondsFromMidnight
			""")
	List<StopTime> findNextArrivals(@Param("stopId")String stopId, @Param("currentSeconds")int currentSeconds, @Param("maxSeconds") int maxSeconds);

}
