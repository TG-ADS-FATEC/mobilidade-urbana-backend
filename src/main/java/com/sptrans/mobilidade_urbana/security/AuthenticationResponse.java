package com.sptrans.mobilidade_urbana.security;

import java.util.UUID;

public class AuthenticationResponse {
	
	private UUID deviceToken;
	private String token;
	
	public AuthenticationResponse(UUID deviceToken, String token) {
		super();
		this.deviceToken = deviceToken;
		this.token = token;
	}

	public UUID getDeviceToken() {
		return deviceToken;
	}

	public String getToken() {
		return token;
	}
	
}
