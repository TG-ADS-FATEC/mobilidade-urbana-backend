package com.sptrans.mobilidade_urbana.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sptrans.mobilidade_urbana.dto.RouteDTO;
import com.sptrans.mobilidade_urbana.dto.RouteItineraryDTO;
import com.sptrans.mobilidade_urbana.dto.StopTimeItineraryDTO;
import com.sptrans.mobilidade_urbana.dto.TripItineraryDTO;
import com.sptrans.mobilidade_urbana.entities.Route;
import com.sptrans.mobilidade_urbana.entities.Trip;
import com.sptrans.mobilidade_urbana.mappers.RouteMapper;
import com.sptrans.mobilidade_urbana.repositories.RouteRepository;
import com.sptrans.mobilidade_urbana.repositories.TripRepository;
import com.sptrans.mobilidade_urbana.services.exceptions.ResourceNotFoundException;

@Service
public class RouteService {
	
	@Autowired
	private RouteRepository repository;
	
	@Autowired
	private TripRepository tripRepository;
	
	@Autowired
	private RouteMapper mapper;
	
	@Transactional(readOnly = true)
	public RouteDTO findById(String routeId) {
		Route route = repository.findById(routeId).orElseThrow(
				() -> new ResourceNotFoundException("Linha não encontrada"));
		return mapper.toDTO(route);
	}
	
	@Transactional(readOnly=true)
	public Page<RouteDTO> findAll(Pageable pageable){
		Page<Route> routes = repository.findAll(pageable);
		return mapper.toDTOPage(routes);
	}
	
	@Transactional(readOnly=true)
	public List<RouteDTO> findAll(){
		List<Route> routes = repository.findAll();
		return mapper.toDTOList(routes);
	}
	
	@Transactional(readOnly=true)
	public Page<RouteDTO> search(String query, Pageable pageable){
		Page<Route> routes = repository.search(query, pageable);
		return mapper.toDTOPage(routes);
	}
	
	@Transactional(readOnly=true)
	public Page<RouteDTO> findByStopId(String stopId, Pageable pageable){
		Page<Route> routes = repository.findRoutesByStopId(stopId, pageable);
		return mapper.toDTOPage(routes);
	}
	
	@Transactional(readOnly=true)
	public RouteItineraryDTO getItinerary(String routeId) {
		
		List<Trip> trips = tripRepository.findTripsByRouteId(routeId);
		
		List<TripItineraryDTO> tripDTOs = trips.stream()
				.map(trip -> new TripItineraryDTO(
						trip.getTripId(),
						
						trip.getStopTimes().stream()
						.map(stopTime -> new StopTimeItineraryDTO(
								
								stopTime.getTrip().getTripId(),
								stopTime.getArrivalTime(),
								stopTime.getDepartureTime(),
								stopTime.getStop().getStopId(),
								stopTime.getStop().getStopName(),
								stopTime.getStopSequence()))
						.toList()
						)).toList();
		
		return new RouteItineraryDTO(routeId, tripDTOs);
		
	}
	

}
