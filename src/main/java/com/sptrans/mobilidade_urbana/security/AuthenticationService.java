package com.sptrans.mobilidade_urbana.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sptrans.mobilidade_urbana.dto.DeviceDTO;
import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.repositories.DeviceRepository;

@Service
public class AuthenticationService {
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	@Autowired
	private TokenService tokenService;
	
	public AuthenticationResponse authenticate(DeviceDTO dto) {
		
		Device device = new Device();
		device.setDeviceToken(dto.getDeviceToken());
		device.setAppVersion(dto.getAppVersion());
		device.setPlatform(dto.getPlatform());
		
		Device saved = deviceRepository.save(device);
		
		String token = tokenService.generateToken(saved);
		
		return new AuthenticationResponse(saved.getDeviceToken(), token);
	}

}
