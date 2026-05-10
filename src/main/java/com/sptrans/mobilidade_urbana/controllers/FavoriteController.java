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

import com.sptrans.mobilidade_urbana.dto.FavoriteDTO;
import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.services.FavoriteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value="/profiles/favorites")
public class FavoriteController {
	
	@Autowired
	private FavoriteService service;
	
	@GetMapping(value = "/me")
	public ResponseEntity<List<FavoriteDTO>> findMyFavorite(@AuthenticationPrincipal Device device){
		List<FavoriteDTO> dto = service.findByDevice(device);
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping
	public ResponseEntity<FavoriteDTO> insert(@AuthenticationPrincipal Device device, @Valid @RequestBody FavoriteDTO dto) {
		FavoriteDTO result = service.insert(device, dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{favoriteId}")
				.buildAndExpand(dto.getFavoriteId()).toUri();
		return ResponseEntity.created(uri).body(result);
	}
	
	@PutMapping(value = "/{favoriteId}")
	public ResponseEntity<FavoriteDTO> update(@AuthenticationPrincipal Device device,@PathVariable Long favoriteId, @Valid @RequestBody FavoriteDTO dto) {
		FavoriteDTO updated = service.update(device, favoriteId, dto);
		return ResponseEntity.ok(updated);
	}
	
	@DeleteMapping(value = "/{favoriteId}")
	public ResponseEntity<Void> delete(@AuthenticationPrincipal Device device, @PathVariable Long favoriteId) {
		service.delete(device, favoriteId);
		return ResponseEntity.noContent().build();
	}

}
