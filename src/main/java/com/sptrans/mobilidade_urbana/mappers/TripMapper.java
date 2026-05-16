package com.sptrans.mobilidade_urbana.mappers;

import org.springframework.stereotype.Component;

import com.sptrans.mobilidade_urbana.dto.TripDTO;
import com.sptrans.mobilidade_urbana.entities.Calendar;
import com.sptrans.mobilidade_urbana.entities.Route;
import com.sptrans.mobilidade_urbana.entities.Shape;
import com.sptrans.mobilidade_urbana.entities.Trip;
import com.sptrans.mobilidade_urbana.repositories.CalendarRepository;
import com.sptrans.mobilidade_urbana.repositories.RouteRepository;
import com.sptrans.mobilidade_urbana.repositories.ShapeRepository;

@Component
public class TripMapper {
	
	private final RouteRepository routeRepository;
	private final CalendarRepository calendarRepository;
	private final ShapeRepository shapeRepository;
	
	public TripMapper(RouteRepository routeRepository, CalendarRepository calendarRepository,
			ShapeRepository shapeRepository) {
		super();
		this.routeRepository = routeRepository;
		this.calendarRepository = calendarRepository;
		this.shapeRepository = shapeRepository;
	}
	
	public Trip toEntity(TripDTO dto) {
		
		Trip trip = new Trip();
		
		trip.setTripId(dto.getTripId());
		trip.setTripHeadsign(dto.getTripHeadsign());
		trip.setDirectionId(dto.getDirectionId());
		
		Route route = routeRepository.getReferenceById(dto.getRouteId());
		trip.setRoute(route);
		
		Calendar calendar = calendarRepository.getReferenceById(dto.getServiceId());
		trip.setCalendar(calendar);
		
		Shape shape = shapeRepository.getReferenceById(dto.getShapeId());
		trip.setShape(shape);
		
		return trip;
	}
	

}
