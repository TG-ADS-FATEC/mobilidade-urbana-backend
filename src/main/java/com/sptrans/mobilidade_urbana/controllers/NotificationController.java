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

import com.sptrans.mobilidade_urbana.dto.NotificationDTO;
import com.sptrans.mobilidade_urbana.dto.NotificationRequestDTO;
import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.services.NotificationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value="/profiles/notifications")
public class NotificationController {
	
	@Autowired
	private NotificationService service;
	
	@GetMapping(value = "/me")
	public ResponseEntity<List<NotificationDTO>> findMyNotifications(@AuthenticationPrincipal Device device){
		List<NotificationDTO> dto = service.findByDevice(device);
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping
	public ResponseEntity<NotificationDTO> insert(@AuthenticationPrincipal Device device, @Valid @RequestBody NotificationRequestDTO dto) {
		NotificationDTO result = service.insert(device, dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{notificationId}")
				.buildAndExpand(result.getNotificationId()).toUri();
		return ResponseEntity.created(uri).body(result);
	}
	
	@PutMapping(value = "/{notificationId}")
	public ResponseEntity<NotificationDTO> update(@AuthenticationPrincipal Device device,@PathVariable Long notificationId, @Valid @RequestBody NotificationDTO dto) {
		NotificationDTO updated = service.update(device, notificationId, dto);
		return ResponseEntity.ok(updated);
	}
	
	@DeleteMapping(value = "/{notificationId}")
	public ResponseEntity<Void> delete(@AuthenticationPrincipal Device device, @PathVariable Long notificationId) {
		service.delete(device, notificationId);
		return ResponseEntity.noContent().build();
	}

}
