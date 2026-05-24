package com.sptrans.mobilidade_urbana.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sptrans.mobilidade_urbana.dto.TripDTO;
import com.sptrans.mobilidade_urbana.entities.Trip;
import com.sptrans.mobilidade_urbana.mappers.TripMapper;
import com.sptrans.mobilidade_urbana.repositories.TripRepository;
import com.sptrans.mobilidade_urbana.services.exceptions.ResourceNotFoundException;

@Service
public class TripService {
	
	@Autowired
	private TripRepository repository;
	
	@Autowired
	private TripMapper mapper;
	
	@Transactional(readOnly = true)
	public TripDTO findById(String tripId) {
		Trip trip = repository.findById(tripId).orElseThrow(
				() -> new ResourceNotFoundException("Viagem não encontrada"));
		return mapper.toDTO(trip);
	}
	
	@Transactional(readOnly=true)
	public Page<TripDTO> findAll(Pageable pageable){
		Page<Trip> trips = repository.findAll(pageable);
		return mapper.toDTOPage(trips);
	}

}
