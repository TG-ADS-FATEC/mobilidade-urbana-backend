package com.sptrans.mobilidade_urbana.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sptrans.mobilidade_urbana.dto.DeviceDTO;
import com.sptrans.mobilidade_urbana.security.AuthenticationResponse;
import com.sptrans.mobilidade_urbana.security.AuthenticationService;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping("/devices")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody DeviceDTO dto){
		
		AuthenticationResponse response = authenticationService.authenticate(dto);
		return ResponseEntity.ok(response);
	}

}
