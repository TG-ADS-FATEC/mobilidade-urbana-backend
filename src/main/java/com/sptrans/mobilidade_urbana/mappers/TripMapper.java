package com.sptrans.mobilidade_urbana.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.sptrans.mobilidade_urbana.dto.TripDTO;
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
	
	public TripDTO toDTO(Trip entity) {
		
		if(entity==null) {
			return null;
		}
		
		return new TripDTO(
				entity.getTripId(),
				entity.getTripHeadsign(),
				entity.getDirectionId(),
				entity.getRoute().getRouteId(),
				entity.getCalendar().getServiceId(),
				entity.getShape().getShapeId());
		
	}
	
	public List<TripDTO> toDTOList(List<Trip> entities) {
		if(entities == null || entities.isEmpty()) {
			return new ArrayList<>();
		}
		
		return entities.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}
	
	public Page<TripDTO> toDTOPage(Page<Trip> page) {
		return page.map(this::toDTO);
	}
	

}
