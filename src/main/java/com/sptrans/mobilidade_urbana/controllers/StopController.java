package com.sptrans.mobilidade_urbana.controllers;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sptrans.mobilidade_urbana.dto.ArrivalDTO;
import com.sptrans.mobilidade_urbana.dto.StopDTO;
import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.pagination.PageMapper;
import com.sptrans.mobilidade_urbana.pagination.PageResponseDTO;
import com.sptrans.mobilidade_urbana.security.SecurityConfiguration;
import com.sptrans.mobilidade_urbana.services.StopService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/stops")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class StopController {
	
	@Autowired
	private StopService service;
	
	@Operation(summary = "Busca dados da parada", description = "Método para buscar uma parada por ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Parada retornada"),
			@ApiResponse(responseCode = "401", description = "Acesso não autorizado ou token inválido"),
			@ApiResponse(responseCode = "400", description = "Parâmetros incorretos"),
			@ApiResponse(responseCode = "404", description = "Parada não encontrada"),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor")
			})
	@GetMapping(value = "/{stopId}")
	public ResponseEntity<StopDTO> findById(@AuthenticationPrincipal Device device, @PathVariable String stopId){
		StopDTO dto = service.findById(stopId);
		return ResponseEntity.ok(dto);
	}
	
	@Operation(summary = "Busca as paradas com paginação", description = "Método que retorna as paradas paginadas")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Paradas retornadas"),
			@ApiResponse(responseCode = "401", description = "Acesso não autorizado ou token inválido"),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor")
			})
	@GetMapping
	public ResponseEntity<PageResponseDTO<StopDTO>> findAll(@AuthenticationPrincipal Device device, @ParameterObject Pageable pageable){
		Page<StopDTO> stops = service.findAll(pageable);
		return ResponseEntity.ok(
				PageMapper.from(stops));
	}
	
	@Operation(summary = "Busca as paradas pela linha", description = "Método que retorna as paradas por linha")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Paradas retornadas"),
			@ApiResponse(responseCode = "401", description = "Acesso não autorizado ou token inválido"),
			@ApiResponse(responseCode = "400", description = "Parâmetros incorretos"),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor")
			})
	@GetMapping(value="/{routeId}/stops")
	public ResponseEntity<PageResponseDTO<StopDTO>> findStopsByRoute(@AuthenticationPrincipal Device device,@PathVariable String routeId, @ParameterObject Pageable pageable){
		Page<StopDTO> stops = service.findStopsByRoute(routeId, pageable);
		return ResponseEntity.ok(PageMapper.from(stops));
	}
	
	@Operation(summary = "Retorna os horários dos ônibus de uma parada", description = "Método que retorna os horários previstos dos próximos ônibus")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Horários retornados"),
			@ApiResponse(responseCode = "401", description = "Acesso não autorizado ou token inválido"),
			@ApiResponse(responseCode = "400", description = "Parâmetros incorretos"),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor")
			})
	@GetMapping(value="{stopId}/arrivals")
	public List<ArrivalDTO> getArrivals(@AuthenticationPrincipal Device device, @PathVariable String stopId){
		return service.getArrivals(stopId);
	}
	
	@Operation(summary = "Busca as paradas próximas do usuário", description = "Método que retorna as paradas próximas em um raio de 500 metros por padrão")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Paradas próximas retornadas"),
			@ApiResponse(responseCode = "401", description = "Acesso não autorizado ou token inválido"),
			@ApiResponse(responseCode = "400", description = "Parâmetros de coordenadas incorretos"),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor")
			})
	@GetMapping(value="/nearby")
	public ResponseEntity<PageResponseDTO<StopDTO>> findNearbyStops(@AuthenticationPrincipal Device device, @RequestParam double latitude, @RequestParam double longitude, @RequestParam(defaultValue="500.00") double radius, @ParameterObject Pageable pageable){
		Page<StopDTO> stops = service.findNearbyStops(latitude, longitude, radius, pageable);
		return ResponseEntity.ok(PageMapper.from(stops));
	}

}
