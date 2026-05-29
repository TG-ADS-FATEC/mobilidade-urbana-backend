package com.sptrans.mobilidade_urbana.controllers;

import java.net.URI;
import java.util.UUID;

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

import com.sptrans.mobilidade_urbana.dto.PreferenceDTO;
import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.security.SecurityConfiguration;
import com.sptrans.mobilidade_urbana.services.PreferenceService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value="/preferences")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class PreferenceController {
	
	@Autowired
	private PreferenceService service;
	
	@GetMapping(value = "/{preferenceId}")
	public ResponseEntity<PreferenceDTO> findById(@PathVariable UUID preferenceId){
		PreferenceDTO dto = service.findById(preferenceId);
		return ResponseEntity.ok(dto);
	}
	
	
	@GetMapping(value = "/me")
	public ResponseEntity<PreferenceDTO> findMyPreference(@AuthenticationPrincipal Device device){
		PreferenceDTO dto = service.findByDevice(device);
		return ResponseEntity.ok(dto);
	}
	
	/*@PostMapping
	public ResponseEntity<PreferenceDTO> insert(@Valid @RequestBody PreferenceDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{preferenceId}")
				.buildAndExpand(dto.getPreferenceId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}*/
	
	@PostMapping
	public ResponseEntity<PreferenceDTO> insert(@AuthenticationPrincipal Device device, @Valid @RequestBody PreferenceDTO dto) {
		PreferenceDTO result = service.insert(device, dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{preferenceId}")
				.buildAndExpand(dto.getPreferenceId()).toUri();
		return ResponseEntity.created(uri).body(result);
	}
	
	@PutMapping(value = "/{preferenceId}")
	public ResponseEntity<PreferenceDTO> update(@PathVariable UUID preferenceId, @Valid @RequestBody PreferenceDTO dto) {
		dto = service.update(preferenceId, dto);
		return ResponseEntity.ok(dto);
	}
	
	@PutMapping(value = "/me")
	public ResponseEntity<PreferenceDTO> update(@AuthenticationPrincipal Device device, @Valid @RequestBody PreferenceDTO dto) {
		PreferenceDTO updated = service.update(device, dto);
		
		return ResponseEntity.ok(updated);
	}
	
	@DeleteMapping(value = "/{preferenceId}")
	public ResponseEntity<Void> delete(@PathVariable UUID preferenceId) {
		service.delete(preferenceId);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/me")
	public ResponseEntity<Void> delete(@AuthenticationPrincipal Device device) {
		service.delete(device);
		return ResponseEntity.noContent().build();
	}

}
