package com.sptrans.mobilidade_urbana.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
	
	public AuthenticationResponse registerDevice(DeviceDTO dto) {
		try {
			Device device = deviceRepository.findByDeviceToken(dto.getDeviceToken())
					.orElseGet(() -> {
						Device newDevice = new Device();
						newDevice.setDeviceToken(dto.getDeviceToken());
						newDevice.setAppVersion(dto.getAppVersion());
						newDevice.setPlatform(dto.getPlatform());
						newDevice.setActive(true);
						newDevice.setTokenVersion(0);
						
						return deviceRepository.save(newDevice);
								
					});
			
			String token = tokenService.generateToken(device);
			
			return new AuthenticationResponse(device.getDeviceId(), token);
		}
		catch(DataIntegrityViolationException e) {
			
			Device device = deviceRepository.findByDeviceToken(dto.getDeviceToken())
					.orElseThrow(() -> new RuntimeException("Erro ao encontrar dispositivo"));
			String token = tokenService.generateToken(device);
			
			return new AuthenticationResponse(device.getDeviceId(), token);
		}
		
	}
	
	public AuthenticationResponse refreshToken(TokenData data) {
		
		Device device = deviceRepository.findById(data.deviceId())
				.orElseThrow(() -> new RuntimeException("Dispositivo não encontrado"));
		
		if (!device.getActive()) {
			throw new RuntimeException("Dispositivo desativado");
		}
		
		if (!device.getTokenVersion().equals(data.tokenVersion())) {
			throw new RuntimeException("Token inválido");
		}
		
		device.setTokenVersion(device.getTokenVersion() + 1);
		deviceRepository.save(device);
		
		String newToken = tokenService.generateToken(device);
		
		return new AuthenticationResponse(device.getDeviceId(), newToken);
	}

}
