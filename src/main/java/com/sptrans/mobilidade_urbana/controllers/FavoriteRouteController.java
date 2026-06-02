package com.sptrans.mobilidade_urbana.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sptrans.mobilidade_urbana.dto.FavoriteRouteDTO;
import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.security.SecurityConfiguration;
import com.sptrans.mobilidade_urbana.services.FavoriteRouteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value="/profiles/favorites/routes")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class FavoriteRouteController {
	
	@Autowired
	private FavoriteRouteService service;
	
	@Operation(summary = "Busca os favoritos do usuário", description = "Método que retorna uma lista das linhas favoritas do usuário")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Favoritos retornados com sucesso"),
			@ApiResponse(responseCode = "401", description = "Acesso não autorizado ou token inválido"),
			@ApiResponse(responseCode = "404", description = "Perfil não encontrado"),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor")
			})
	@GetMapping
	public ResponseEntity<List<FavoriteRouteDTO>> findMyFavorite(@AuthenticationPrincipal Device device){
		List<FavoriteRouteDTO> dto = service.findByDevice(device);
		return ResponseEntity.ok(dto);
	}
	
	@Operation(summary = "Insere um favorito", description = "Método que insere um favorito a partir do ID da linha")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Favorito criado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Parâmetros incorretos"),
			@ApiResponse(responseCode = "401", description = "Acesso não autorizado ou token inválido"),
			@ApiResponse(responseCode = "404", description = "Perfil não encontrado"),
			@ApiResponse(responseCode = "409", description = "Linha não encontrada"),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor")
			})
	@PostMapping
	public ResponseEntity<FavoriteRouteDTO> insert(@AuthenticationPrincipal Device device, @Valid @RequestBody FavoriteRouteDTO dto) {
		FavoriteRouteDTO result = service.insert(device, dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{favoriteRouteId}")
				.buildAndExpand(dto.getFavoriteRouteId()).toUri();
		return ResponseEntity.created(uri).body(result);
	}
	
	@Operation(summary = "Exclui um favorito", description = "Método que exclui um favorito")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Favorito excluído com sucesso"),
			@ApiResponse(responseCode = "400", description = "Parâmetros incorretos"),
			@ApiResponse(responseCode = "401", description = "Acesso não autorizado ou token inválido"),
			@ApiResponse(responseCode = "404", description = "Favorito não encontrado"),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor")
			})
	@DeleteMapping(value = "/{favoriteRouteId}")
	public ResponseEntity<Void> delete(@AuthenticationPrincipal Device device, @PathVariable Long favoriteRouteId) {
		service.delete(device, favoriteRouteId);
		return ResponseEntity.noContent().build();
	}

}
