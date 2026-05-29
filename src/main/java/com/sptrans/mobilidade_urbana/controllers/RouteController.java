package com.sptrans.mobilidade_urbana.controllers;

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

import com.sptrans.mobilidade_urbana.dto.RouteDTO;
import com.sptrans.mobilidade_urbana.dto.RouteItineraryDTO;
import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.pagination.PageMapper;
import com.sptrans.mobilidade_urbana.pagination.PageResponseDTO;
import com.sptrans.mobilidade_urbana.security.SecurityConfiguration;
import com.sptrans.mobilidade_urbana.services.RouteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/routes")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class RouteController {
	
	@Autowired
	private RouteService service;
	
	@Operation(summary = "Busca uma linha por ID", description = "Método que retorna uma linha pelo seu ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Linha retornada com sucesso"),
			@ApiResponse(responseCode = "401", description = "Acesso não autorizado ou token inválido"),
			@ApiResponse(responseCode = "400", description = "Parâmetros incorretos"),
			@ApiResponse(responseCode = "404", description = "Linha não encontrada"),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor")
			})
	@GetMapping(value = "/{routeId}")
	public ResponseEntity<RouteDTO> findById(@AuthenticationPrincipal Device device, @PathVariable String routeId){
		RouteDTO dto = service.findById(routeId);
		return ResponseEntity.ok(dto);
	}
	
	@Operation(summary = "Busca paginada de linhas de ônibus", description = "Método que realiza uma busca paginada de linhas de ônibus")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Linhas retornadas com sucesso"),
			@ApiResponse(responseCode = "401", description = "Acesso não autorizado ou token inválido"),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor")
			})
	@GetMapping
	public ResponseEntity<PageResponseDTO<RouteDTO>> findAll(@AuthenticationPrincipal Device device, @ParameterObject Pageable pageable){
		Page<RouteDTO> routes = service.findAll(pageable);
		return ResponseEntity.ok(
				PageMapper.from(routes));
	}
	
	@Operation(summary = "Pesquisar linhas de ônibus", description = " Método de pesquisar uma linha pelo seu nome curto ou nome longo")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Busca da linha realizada com sucesso"),
			@ApiResponse(responseCode = "400", description = "Parâmetros incorretos ou faltando"),
			@ApiResponse(responseCode = "401", description = "Acesso não autorizado ou token inválido"),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor")
			})
	@GetMapping("/search")
	public ResponseEntity<PageResponseDTO<RouteDTO>> search(@AuthenticationPrincipal Device device, @RequestParam String query, @ParameterObject Pageable pageable){
		Page<RouteDTO> routes = service.search(query, pageable);
		return ResponseEntity.ok(PageMapper.from(routes));
	}
	
	@Operation(summary = "Retorna as linhas por parada", description = "Método que retorna as linhas de uma parada")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Linhas da parada solicitada retornadas com sucesso"),
			@ApiResponse(responseCode = "401", description = "Acesso não autorizado ou token inválido"),
			@ApiResponse(responseCode = "400", description = "Parâmetros incorretos"),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor")
			})
	@GetMapping("/stops/{stopId}/routes")
	public ResponseEntity<PageResponseDTO<RouteDTO>> findRoutesByStop(@AuthenticationPrincipal Device device, @PathVariable String stopId, @ParameterObject Pageable pageable){
		Page<RouteDTO> routes = service.findByStopId(stopId, pageable);
		return ResponseEntity.ok(PageMapper.from(routes));
	}
	
	@Operation(summary = "Retorna o itinerário de uma linha", description = "Método que retorna o itinerário de uma linha")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Itinerário da rota solicitada retornado com sucesso"),
			@ApiResponse(responseCode = "401", description = "Acesso não autorizado ou token inválido"),
			@ApiResponse(responseCode = "400", description = "Parâmetros incorretos"),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor")
			})
	@GetMapping("/{routeId}/itinerary")
	public ResponseEntity<RouteItineraryDTO> getItinerary(@AuthenticationPrincipal Device device, @PathVariable String routeId){
		return ResponseEntity.ok(service.getItinerary(routeId));
	}

}
