package com.sptrans.mobilidade_urbana.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sptrans.mobilidade_urbana.dto.RouteDTO;
import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.pagination.PageMapper;
import com.sptrans.mobilidade_urbana.pagination.PageResponseDTO;
import com.sptrans.mobilidade_urbana.services.RouteService;

@RestController
@RequestMapping("/routes")
public class RouteController {
	
	@Autowired
	private RouteService service;
	
	@GetMapping(value = "/{routeId}")
	public ResponseEntity<RouteDTO> findById(@AuthenticationPrincipal Device device, @PathVariable String routeId){
		RouteDTO dto = service.findById(routeId);
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping
	public ResponseEntity<PageResponseDTO<RouteDTO>> findAll(@AuthenticationPrincipal Device device, Pageable pageable){
		Page<RouteDTO> routes = service.findAll(pageable);
		return ResponseEntity.ok(
				PageMapper.from(routes));
	}
	
	@GetMapping("/search")
	public ResponseEntity<PageResponseDTO<RouteDTO>> search(@AuthenticationPrincipal Device device, @RequestParam String query, Pageable pageable){
		Page<RouteDTO> routes = service.search(query, pageable);
		return ResponseEntity.ok(PageMapper.from(routes));
	}

}
