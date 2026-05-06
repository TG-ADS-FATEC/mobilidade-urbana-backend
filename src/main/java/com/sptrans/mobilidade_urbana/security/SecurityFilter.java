package com.sptrans.mobilidade_urbana.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.repositories.DeviceRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private DeviceRepository deviceRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = this.recoverToken(request);
		if(token == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		try {
			TokenData data = tokenService.validateToken(token);
			
			if(data==null || data.deviceId()==null) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
			
			Device device = deviceRepository.findById(data.deviceId())
					.orElseThrow(() -> new RuntimeException("Dispositivo inválido"));
			
			
			if(device == null) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
			
			if(!device.getActive()) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
			
			if(!device.getTokenVersion().equals(data.tokenVersion())) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
			
			var authentication = new UsernamePasswordAuthenticationToken(device, null, List.of());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		catch(Exception e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		filterChain.doFilter(request, response);
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return request.getServletPath().startsWith("/authentication/devices");
	}
	
	private String recoverToken(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		if(authHeader == null || !authHeader.startsWith("Bearer ")) return null;
		return authHeader.substring(7);
	}

}
