package com.sptrans.mobilidade_urbana.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sptrans.mobilidade_urbana.dto.FrequencyDTO;
import com.sptrans.mobilidade_urbana.entities.Frequency;
import com.sptrans.mobilidade_urbana.mappers.FrequencyMapper;
import com.sptrans.mobilidade_urbana.repositories.FrequencyRepository;
import com.sptrans.mobilidade_urbana.services.exceptions.ResourceNotFoundException;

@Service
public class FrequencyService {
	
	@Autowired
	FrequencyRepository repository;
	
	@Autowired
	FrequencyMapper mapper;
	
	@Transactional(readOnly = true)
	public FrequencyDTO findById(Long frequencyId) {
		Frequency frequency = repository.findById(frequencyId).orElseThrow(
				() -> new ResourceNotFoundException("Tempo entre viagens não encontrado"));
		return mapper.toDTO(frequency);
	}
	
	@Transactional(readOnly=true)
	public Page<FrequencyDTO> findAll(Pageable pageable){
		Page<Frequency> frequencies = repository.findAll(pageable);
		return mapper.toDTOPage(frequencies);
	}

}
