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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sptrans.mobilidade_urbana.dto.DeviceDTO;
import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.services.DeviceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/devices")
public class DeviceController {
	
	@Autowired
	private DeviceService service;
	
	@GetMapping(value = "/{deviceId}")
	public ResponseEntity<DeviceDTO> findById(@PathVariable UUID deviceId){
		DeviceDTO dto = service.findById(deviceId);
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping(value = "/me")
	public ResponseEntity<DeviceDTO> findById(@AuthenticationPrincipal Device device){
		DeviceDTO dto = service.findById(device.getDeviceId());
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping
	public ResponseEntity<DeviceDTO> insert(@Valid @RequestBody DeviceDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{deviceId}")
				.buildAndExpand(dto.getDeviceId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@DeleteMapping(value = "/{deviceId}")
	public ResponseEntity<Void> delete(@PathVariable UUID deviceId) {
		service.delete(deviceId);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/me")
	public ResponseEntity<Void> delete(@AuthenticationPrincipal Device device) {
		service.delete(device.getDeviceId());
		return ResponseEntity.noContent().build();
	}

}
