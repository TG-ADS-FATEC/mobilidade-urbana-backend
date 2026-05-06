package com.sptrans.mobilidade_urbana.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sptrans.mobilidade_urbana.entities.Device;

@Service
public class TokenService {
	@Value("${api.security.token.secret}")
	private String secret;
	
	public String generateToken(Device device) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create()
					.withIssuer("auth-api")
					.withSubject(device.getDeviceId().toString())
					.withClaim("tV", device.getTokenVersion())
					.withExpiresAt(genExpirationDate())
					.sign(algorithm);
			return token;
		}
		catch (JWTCreationException exception) {
			throw new RuntimeException("Erro durante geração do token", exception);
		}
	}
	
	public TokenData validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			
			var decodedJWT = JWT.require(algorithm)
					.withIssuer("auth-api")
					.build()
					.verify(token);
			
			UUID deviceId = UUID.fromString(decodedJWT.getSubject());
			Integer tokenVersion = decodedJWT.getClaim("tV").asInt();
					
			return new TokenData(deviceId, tokenVersion);
		}
		catch(JWTVerificationException exception) {
			throw new RuntimeException("Token inválido ou expirado", exception);
		}
		
	}
	
	private Instant genExpirationDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}

}
