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
import com.sptrans.mobilidade_urbana.security.TokenData;
import com.sptrans.mobilidade_urbana.security.TokenService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/devices/register")
	public ResponseEntity<AuthenticationResponse> registerDevice(@RequestBody DeviceDTO dto){
		
		AuthenticationResponse response = authenticationService.registerDevice(dto);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/devices/refresh")
	public ResponseEntity<AuthenticationResponse> refresh(HttpServletRequest request){
		String token = request.getHeader("Authorization").substring(7);
		
		TokenData data = tokenService.validateToken(token);
		
		AuthenticationResponse response = authenticationService.refreshToken(data);
		
		return ResponseEntity.ok(response);
	}

}
