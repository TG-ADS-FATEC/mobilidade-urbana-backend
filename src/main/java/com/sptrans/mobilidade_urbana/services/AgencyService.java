package com.sptrans.mobilidade_urbana.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sptrans.mobilidade_urbana.dto.AgencyDTO;
import com.sptrans.mobilidade_urbana.entities.Agency;
import com.sptrans.mobilidade_urbana.mappers.AgencyMapper;
import com.sptrans.mobilidade_urbana.repositories.AgencyRepository;
import com.sptrans.mobilidade_urbana.services.exceptions.ResourceNotFoundException;

@Service
public class AgencyService {
	
	@Autowired
	private AgencyRepository repository;
	
	@Autowired
	private AgencyMapper mapper;
	
	@Transactional(readOnly = true)
	public AgencyDTO findById(String agencyId) {
		Agency agency = repository.findById(agencyId).orElseThrow(
				() -> new ResourceNotFoundException("Agencia não encontrada"));
		return mapper.toDTO(agency);
	}

}
