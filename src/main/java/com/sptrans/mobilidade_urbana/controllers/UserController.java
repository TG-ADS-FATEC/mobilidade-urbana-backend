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

import com.sptrans.mobilidade_urbana.dto.UserDTO;
import com.sptrans.mobilidade_urbana.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value="/users")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@GetMapping(value = "/{userId}")
	public ResponseEntity<UserDTO> findById(@PathVariable Long userId){
		UserDTO dto = service.findById(userId);
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userId}")
				.buildAndExpand(dto.getUserId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{userId}")
	public ResponseEntity<UserDTO> update(@PathVariable Long userId, @Valid @RequestBody UserDTO dto) {
		dto = service.update(userId, dto);
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping(value = "/{userId}")
	public ResponseEntity<Void> delete(@PathVariable Long userId) {
		service.delete(userId);
		return ResponseEntity.noContent().build();
	}
	

}
