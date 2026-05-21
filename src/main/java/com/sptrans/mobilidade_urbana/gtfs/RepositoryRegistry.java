package com.sptrans.mobilidade_urbana.gtfs;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.sptrans.mobilidade_urbana.entities.Agency;
import com.sptrans.mobilidade_urbana.entities.Calendar;
import com.sptrans.mobilidade_urbana.entities.Frequency;
import com.sptrans.mobilidade_urbana.entities.Route;
import com.sptrans.mobilidade_urbana.entities.Stop;
import com.sptrans.mobilidade_urbana.entities.StopTime;
import com.sptrans.mobilidade_urbana.entities.Trip;
import com.sptrans.mobilidade_urbana.repositories.AgencyRepository;
import com.sptrans.mobilidade_urbana.repositories.CalendarRepository;
import com.sptrans.mobilidade_urbana.repositories.FrequencyRepository;
import com.sptrans.mobilidade_urbana.repositories.RouteRepository;
import com.sptrans.mobilidade_urbana.repositories.ShapeRepository;
import com.sptrans.mobilidade_urbana.repositories.StopRepository;
import com.sptrans.mobilidade_urbana.repositories.StopTimeRepository;
import com.sptrans.mobilidade_urbana.repositories.TripRepository;

@Component
public class RepositoryRegistry {
	
	private final Map<Class<?>, JpaRepository<?,?>> map = new HashMap<>();
	
	public RepositoryRegistry(
			AgencyRepository agencyRepository,
			RouteRepository routeRepository,
			CalendarRepository calendarRepository,
			StopRepository stopRepository,
			ShapeRepository shapeRepository,
			TripRepository tripRepository,
			FrequencyRepository frequencyRepository,
			StopTimeRepository stopTimeRepository) {
		map.put(Agency.class, agencyRepository);
		map.put(Route.class, routeRepository);
		map.put(Calendar.class, calendarRepository);
		map.put(Stop.class, stopRepository);
		map.put(Shape.class, shapeRepository);
		map.put(Trip.class, tripRepository);
		map.put(Frequency.class, frequencyRepository);
		map.put(StopTime.class, stopTimeRepository);
	}
	
	@SuppressWarnings("unchecked")
	public <T> JpaRepository<T, ?> get(Class<T> clas){
		return (JpaRepository<T,?>) map.get(clas);
	}

}
