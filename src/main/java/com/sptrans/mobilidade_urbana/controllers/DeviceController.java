package com.sptrans.mobilidade_urbana.controllers;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sptrans.mobilidade_urbana.dto.DeviceDTO;
import com.sptrans.mobilidade_urbana.services.DeviceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/devices")
public class DeviceController {
	
	@Autowired
	private DeviceService service;
	
	@GetMapping(value = "/{deviceToken}")
	public ResponseEntity<DeviceDTO> findById(@PathVariable UUID deviceToken){
		DeviceDTO dto = service.findById(deviceToken);
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping
	public ResponseEntity<DeviceDTO> insert(@Valid @RequestBody DeviceDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{deviceToken}")
				.buildAndExpand(dto.getDeviceToken()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@DeleteMapping(value = "/{deviceToken}")
	public ResponseEntity<Void> delete(@PathVariable UUID deviceToken) {
		service.delete(deviceToken);
		return ResponseEntity.noContent().build();
	}

}
