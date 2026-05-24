package com.sptrans.mobilidade_urbana.services;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sptrans.mobilidade_urbana.dto.ArrivalDTO;
import com.sptrans.mobilidade_urbana.dto.StopDTO;
import com.sptrans.mobilidade_urbana.entities.Stop;
import com.sptrans.mobilidade_urbana.entities.StopTime;
import com.sptrans.mobilidade_urbana.mappers.StopMapper;
import com.sptrans.mobilidade_urbana.repositories.StopRepository;
import com.sptrans.mobilidade_urbana.repositories.StopTimeRepository;
import com.sptrans.mobilidade_urbana.services.exceptions.ResourceNotFoundException;

@Service
public class StopService {
	
	@Autowired
	private StopRepository repository;
	
	@Autowired
	private StopTimeRepository stopTimeRepository; 
	
	@Autowired
	private StopMapper mapper;
	
	@Transactional(readOnly = true)
	public StopDTO findById(String stopId) {
		Stop stop = repository.findById(stopId).orElseThrow(
				() -> new ResourceNotFoundException("Parada não encontrada"));
		return mapper.toDTO(stop);
	}
	
	@Transactional(readOnly=true)
	public Page<StopDTO> findAll(Pageable pageable){
		Page<Stop> stops = repository.findAll(pageable);
		return mapper.toDTOPage(stops);
	}
	
	@Transactional(readOnly=true)
	public Page<StopDTO> findStopsByRoute(String routeId, Pageable pageable){
		Page<Stop> stops = repository.findStopsByRouteId(routeId, pageable);
		return mapper.toDTOPage(stops);
	}
	
	@Transactional
	public List<ArrivalDTO> getArrivals(String stopId){
		
		int nowSeconds = LocalTime.now().toSecondOfDay();
		
		int max = nowSeconds + (2 * 3600);
		
		List<StopTime> stopTimes = stopTimeRepository.findNextArrivals(stopId, nowSeconds, max);
		
		return stopTimes.stream()
				.map(stopTime -> {
					
					int arrivalSeconds = stopTime.getArrivalTime().getSecondsFromMidnight();
					
					int difference = arrivalSeconds - nowSeconds;
					
					if(difference < nowSeconds) {
						difference += 24 * 3600;
					}
					
					int arrivalInMinutes = difference / 60;
					
				 return new ArrivalDTO(
						stopTime.getTrip().getRoute().getRouteId(),
						stopTime.getTrip().getTripId(),
						stopTime.getStop().getStopId(),
						stopTime.getStop().getStopName(),
						stopTime.getTrip().getDirectionId(),
						stopTime.getArrivalTime(),
						stopTime.getStopSequence(),
						arrivalInMinutes
				);
				 }).toList();
	}

}
