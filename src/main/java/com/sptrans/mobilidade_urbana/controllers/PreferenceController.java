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
	
	@GetMapping(value = "/device/{deviceToken}")
	public ResponseEntity<PreferenceDTO> findByDeviceToken(@PathVariable UUID deviceToken){
		PreferenceDTO dto = service.findByDeviceToken(deviceToken);
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping(value = "/me")
	public ResponseEntity<PreferenceDTO> findMyPreference(@AuthenticationPrincipal Device device){
		PreferenceDTO dto = service.findByDeviceToken(device.getDeviceToken());
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
	
	@PutMapping(value = "/device/{deviceToken}")
	public ResponseEntity<PreferenceDTO> update(@PathVariable UUID deviceToken, @Valid @RequestBody PreferenceDTO dto) {
		dto = service.update(deviceToken, dto);
		return ResponseEntity.ok(dto);
	}
	
	@PutMapping(value = "/me")
	public ResponseEntity<PreferenceDTO> update(@AuthenticationPrincipal Device device, @Valid @RequestBody PreferenceDTO dto) {
		dto = service.update(device.getDeviceToken(), dto);
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping(value = "/{preferenceId}")
	public ResponseEntity<Void> delete(@PathVariable Long preferenceId) {
		service.delete(preferenceId);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/device/{deviceToken}")
	public ResponseEntity<Void> delete(@PathVariable UUID deviceToken) {
		service.delete(deviceToken);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/me")
	public ResponseEntity<Void> delete(@AuthenticationPrincipal Device device) {
		service.delete(device.getDeviceToken());
		return ResponseEntity.noContent().build();
	}

}
