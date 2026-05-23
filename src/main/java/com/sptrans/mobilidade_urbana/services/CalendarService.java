package com.sptrans.mobilidade_urbana.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sptrans.mobilidade_urbana.dto.CalendarDTO;
import com.sptrans.mobilidade_urbana.entities.Calendar;
import com.sptrans.mobilidade_urbana.mappers.CalendarMapper;
import com.sptrans.mobilidade_urbana.repositories.CalendarRepository;
import com.sptrans.mobilidade_urbana.services.exceptions.ResourceNotFoundException;

@Service
public class CalendarService {
	
	@Autowired
	private CalendarRepository repository;
	
	@Autowired
	private CalendarMapper mapper;
	
	@Transactional(readOnly = true)
	public CalendarDTO findById(String calendarId) {
		Calendar calendar = repository.findById(calendarId).orElseThrow(
				() -> new ResourceNotFoundException("Serviço não encontrado"));
		return mapper.toDTO(calendar);
	}

}
