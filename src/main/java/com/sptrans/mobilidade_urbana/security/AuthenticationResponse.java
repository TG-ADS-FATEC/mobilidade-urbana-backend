package com.sptrans.mobilidade_urbana.security;

import java.util.UUID;

public class AuthenticationResponse {
	
	private UUID deviceId;
	private String token;
	
	public AuthenticationResponse(UUID deviceId, String token) {
		super();
		this.deviceId = deviceId;
		this.token = token;
	}

	public UUID getDeviceId() {
		return deviceId;
	}

	public String getToken() {
		return token;
	}
	
}
