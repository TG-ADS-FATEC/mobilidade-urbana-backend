package com.sptrans.mobilidade_urbana.mappers;

import org.springframework.stereotype.Component;

import com.sptrans.mobilidade_urbana.dto.TripRawDTO;
import com.sptrans.mobilidade_urbana.entities.Calendar;
import com.sptrans.mobilidade_urbana.entities.DirectionId;
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
	
	public Trip toEntity(TripRawDTO rawDto) {
		
		if(rawDto==null) {
			return null;
		}
		
		Trip trip = new Trip();
		
		trip.setTripId(rawDto.getTripId());
		trip.setTripHeadsign(rawDto.getTripHeadsign());
		trip.setDirectionId(DirectionId.from(Integer.parseInt(rawDto.getDirectionId())));
		
		Route route = routeRepository.getReferenceById(rawDto.getRouteId());
		trip.setRoute(route);
		
		Calendar calendar = calendarRepository.getReferenceById(rawDto.getServiceId());
		trip.setCalendar(calendar);
		
		Shape shape = shapeRepository.getReferenceById(rawDto.getShapeId());
		trip.setShape(shape);
		
		return trip;
	}
	

}
