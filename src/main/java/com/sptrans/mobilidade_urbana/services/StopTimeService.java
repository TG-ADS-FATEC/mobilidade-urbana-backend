package com.sptrans.mobilidade_urbana.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sptrans.mobilidade_urbana.dto.StopTimeDTO;
import com.sptrans.mobilidade_urbana.entities.StopTime;
import com.sptrans.mobilidade_urbana.entities.StopTimeId;
import com.sptrans.mobilidade_urbana.mappers.StopTimeMapper;
import com.sptrans.mobilidade_urbana.repositories.StopTimeRepository;
import com.sptrans.mobilidade_urbana.services.exceptions.ResourceNotFoundException;

@Service
public class StopTimeService {
	
	@Autowired
	private StopTimeRepository repository;
	
	@Autowired
	private StopTimeMapper mapper;
	
	@Transactional(readOnly = true)
	public StopTimeDTO findById(Long stopTimeId) {
		StopTime stopTime = repository.findById(stopTimeId).orElseThrow(
				() -> new ResourceNotFoundException("Tempo da Parada não encontrada"));
		return mapper.toDTO(stopTime);
	}
	
	@Transactional(readOnly=true)
	public Page<StopTimeDTO> findAll(Pageable pageable){
		Page<StopTime> stopTimes = repository.findAll(pageable);
		return mapper.toDTOPage(stopTimes);
	}

}
