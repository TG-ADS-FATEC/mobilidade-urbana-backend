package com.sptrans.mobilidade_urbana.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sptrans.mobilidade_urbana.dto.AlertDTO;
import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.services.AlertService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value="/profiles/alerts")
public class AlertController {
	
	@Autowired
	private AlertService service;
	
	@GetMapping(value = "/me")
	public ResponseEntity<List<AlertDTO>> findMyAlerts(@AuthenticationPrincipal Device device){
		List<AlertDTO> dto = service.findByDevice(device);
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping
	public ResponseEntity<AlertDTO> insert(@AuthenticationPrincipal Device device, @Valid @RequestBody AlertDTO dto) {
		AlertDTO result = service.insert(device, dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{alertId}")
				.buildAndExpand(dto.getAlertId()).toUri();
		return ResponseEntity.created(uri).body(result);
	}
	
	@PutMapping(value = "/{alertId}")
	public ResponseEntity<AlertDTO> update(@AuthenticationPrincipal Device device,@PathVariable Long alertId, @Valid @RequestBody AlertDTO dto) {
		AlertDTO updated = service.update(device, alertId, dto);
		return ResponseEntity.ok(updated);
	}
	
	@DeleteMapping(value = "/{alertId}")
	public ResponseEntity<Void> delete(@AuthenticationPrincipal Device device, @PathVariable Long alertId) {
		service.delete(device, alertId);
		return ResponseEntity.noContent().build();
	}

}
