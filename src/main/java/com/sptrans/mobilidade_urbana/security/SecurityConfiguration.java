package com.sptrans.mobilidade_urbana.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@EnableWebSecurity
@SecurityScheme(name = SecurityConfiguration.SECURITY, type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class SecurityConfiguration {
	
	public static final String SECURITY = "bearerAuth";
	
	@Autowired
	private SecurityFilter securityFilter;
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, Environment env) throws Exception {
    	
    	boolean isDev = Arrays.asList(env.getActiveProfiles()).contains("dev");
    	
    	httpSecurity
    		.csrf(csrf -> csrf.disable())
   			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
   			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
   	
    	httpSecurity.authorizeHttpRequests(auth -> {
    					auth.requestMatchers(HttpMethod.POST, "/authentication/devices/register").permitAll();
    					auth.requestMatchers(HttpMethod.POST, "/authentication/devices/refresh").permitAll();
    					
    					if(isDev) {
    						auth.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll();
    					}
    					
    					auth.anyRequest().authenticated();
    					});
    	
    	httpSecurity.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
    	
    	return httpSecurity.build();
    }
    
    @Bean 
    public CorsConfigurationSource corsConfigurationSource () {
    	CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOriginPatterns(List.of("http://localhost:8080"));
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
		config.setAllowedHeaders(List.of("*"));
		config.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
    }

}
