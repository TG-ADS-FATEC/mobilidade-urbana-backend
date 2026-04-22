package com.sptrans.mobilidade_urbana.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.sptrans.mobilidade_urbana.services.PreferenceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value="/preferences")
public class PreferenceController {
	
	@Autowired
	private PreferenceService service;
	
	@GetMapping(value = "/{preferenceId}")
	public ResponseEntity<PreferenceDTO> findById(@PathVariable Long preferenceId){
		PreferenceDTO dto = service.findById(preferenceId);
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping
	public ResponseEntity<PreferenceDTO> insert(@Valid @RequestBody PreferenceDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{preferenceId}")
				.buildAndExpand(dto.getPreferenceId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{preferenceId}")
	public ResponseEntity<PreferenceDTO> update(@PathVariable Long preferenceId, @Valid @RequestBody PreferenceDTO dto) {
		dto = service.update(preferenceId, dto);
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping(value = "/{preferenceId}")
	public ResponseEntity<Void> delete(@PathVariable Long preferenceId) {
		service.delete(preferenceId);
		return ResponseEntity.noContent().build();
	}

}
