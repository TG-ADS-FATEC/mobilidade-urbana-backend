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

import com.sptrans.mobilidade_urbana.dto.ProfileDTO;
import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.services.ProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value="/profiles")
public class ProfileController {
	
	@Autowired
	private ProfileService service;
	
	@GetMapping(value = "/{profileId}")
	public ResponseEntity<ProfileDTO> findById(@PathVariable Long profileId){
		ProfileDTO dto = service.findById(profileId);
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping(value = "/device/{deviceToken}")
	public ResponseEntity<ProfileDTO> findByDeviceToken(@PathVariable UUID deviceToken){
		ProfileDTO dto = service.findByDeviceToken(deviceToken);
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping(value = "/me")
	public ResponseEntity<ProfileDTO> findMyProfile(@AuthenticationPrincipal Device device){
		ProfileDTO dto = service.findByDeviceToken(device.getDeviceToken());
		return ResponseEntity.ok(dto);
	}
	
	/*@PostMapping
	public ResponseEntity<ProfileDTO> insert(@Valid @RequestBody ProfileDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{profileId}")
				.buildAndExpand(dto.getprofileId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}*/
	
	@PostMapping
	public ResponseEntity<ProfileDTO> insert(@AuthenticationPrincipal Device device, @Valid @RequestBody ProfileDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{profileId}")
				.buildAndExpand(dto.getprofileId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{profileId}")
	public ResponseEntity<ProfileDTO> update(@PathVariable Long profileId, @Valid @RequestBody ProfileDTO dto) {
		dto = service.update(profileId, dto);
		return ResponseEntity.ok(dto);
	}
	
	@PutMapping(value = "/device/{deviceToken}")
	public ResponseEntity<ProfileDTO> update(@PathVariable UUID deviceToken, @Valid @RequestBody ProfileDTO dto) {
		dto = service.update(deviceToken, dto);
		return ResponseEntity.ok(dto);
	}
	
	@PutMapping(value = "/me")
	public ResponseEntity<ProfileDTO> update(@AuthenticationPrincipal Device device, @Valid @RequestBody ProfileDTO dto) {
		dto = service.update(device.getDeviceToken(), dto);
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping(value = "/{profileId}")
	public ResponseEntity<Void> delete(@PathVariable Long profileId) {
		service.delete(profileId);
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
