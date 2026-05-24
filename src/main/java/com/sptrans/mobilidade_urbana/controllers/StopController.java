package com.sptrans.mobilidade_urbana.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sptrans.mobilidade_urbana.dto.StopDTO;
import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.pagination.PageMapper;
import com.sptrans.mobilidade_urbana.pagination.PageResponseDTO;
import com.sptrans.mobilidade_urbana.services.StopService;

@RestController
@RequestMapping("/stops")
public class StopController {
	
	@Autowired
	private StopService service;
	
	@GetMapping(value = "/{stopId}")
	public ResponseEntity<StopDTO> findById(@AuthenticationPrincipal Device device, @PathVariable String stopId){
		StopDTO dto = service.findById(stopId);
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping
	public ResponseEntity<PageResponseDTO<StopDTO>> findAll(@AuthenticationPrincipal Device device, Pageable pageable){
		Page<StopDTO> stops = service.findAll(pageable);
		return ResponseEntity.ok(
				PageMapper.from(stops));
	}
	
	@GetMapping(value="/{routeId}/stops")
	public ResponseEntity<PageResponseDTO<StopDTO>> findStopsByRoute(@AuthenticationPrincipal Device device,@PathVariable String routeId, Pageable pageable){
		Page<StopDTO> stops = service.findStopsByRoute(routeId, pageable);
		return ResponseEntity.ok(PageMapper.from(stops));
	}

}
