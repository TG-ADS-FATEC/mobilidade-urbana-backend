package com.sptrans.mobilidade_urbana.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sptrans.mobilidade_urbana.dto.RouteDTO;
import com.sptrans.mobilidade_urbana.entities.Route;
import com.sptrans.mobilidade_urbana.mappers.RouteMapper;
import com.sptrans.mobilidade_urbana.repositories.RouteRepository;
import com.sptrans.mobilidade_urbana.services.exceptions.ResourceNotFoundException;

@Service
public class RouteService {
	
	@Autowired
	private RouteRepository repository;
	
	@Autowired
	private RouteMapper mapper;
	
	@Transactional(readOnly = true)
	public RouteDTO findById(String routeId) {
		Route route = repository.findById(routeId).orElseThrow(
				() -> new ResourceNotFoundException("Rota não encontrada"));
		return mapper.toDTO(route);
	}
	
	@Transactional(readOnly=true)
	public Page<RouteDTO> findAll(Pageable pageable){
		Page<Route> routes = repository.findAll(pageable);
		return mapper.toDTOPage(routes);
	}
	
	@Transactional(readOnly=true)
	public List<RouteDTO> findAll(){
		return repository.findAll()
				.stream()
				.map(mapper::toDTO)
				.toList();
	}
	
	@Transactional(readOnly=true)
	public Page<RouteDTO> search(String query, Pageable pageable){
		return repository.search(query, pageable).map(mapper::toDTO);
	}
	

}
