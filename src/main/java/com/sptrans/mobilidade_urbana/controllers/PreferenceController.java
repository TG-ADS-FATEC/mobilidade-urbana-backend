package com.sptrans.mobilidade_urbana.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sptrans.mobilidade_urbana.dto.PreferenceDTO;
import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.security.SecurityConfiguration;
import com.sptrans.mobilidade_urbana.services.PreferenceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value="/preferences")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class PreferenceController {
	
	@Autowired
	private PreferenceService service;
	
	@Operation(summary = "Busca as preferências do usuário", description = "Método que retorna as preferências do usuário")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Preferências retornadas com sucesso"),
			@ApiResponse(responseCode = "401", description = "Acesso não autorizado ou token inválido"),
			@ApiResponse(responseCode = "404", description = "Preferência não encontrada"),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor")
			})
	@GetMapping
	public ResponseEntity<PreferenceDTO> findMyPreference(@AuthenticationPrincipal Device device){
		PreferenceDTO dto = service.findByDeviceId(device.getDeviceId());
		return ResponseEntity.ok(dto);
	}
	
	@Operation(summary = "Insere uma preferência", description = "Método que insere uma preferência")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Preferência criada com sucesso"),
			@ApiResponse(responseCode = "400", description = "Parâmetros incorretos"),
			@ApiResponse(responseCode = "401", description = "Acesso não autorizado ou token inválido"),
			@ApiResponse(responseCode = "409", description = "Preferência já existente"),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor")
			})
	@PostMapping
	public ResponseEntity<PreferenceDTO> insert(@AuthenticationPrincipal Device device, @Valid @RequestBody PreferenceDTO dto) {
		PreferenceDTO result = service.insert(device, dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		return ResponseEntity.created(uri).body(result);
	}
	
	@Operation(summary = "Atualiza uma preferência", description = "Método que atualiza uma preferência")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Preferência atualizado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Parâmetros incorretos"),
			@ApiResponse(responseCode = "401", description = "Acesso não autorizado ou token inválido"),
			@ApiResponse(responseCode = "404", description = "Preferência não encontrada"),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor")
			})
	@PutMapping
	public ResponseEntity<PreferenceDTO> update(@AuthenticationPrincipal Device device, @Valid @RequestBody PreferenceDTO dto) {
		PreferenceDTO updated = service.update(device, dto);
		
		return ResponseEntity.ok(updated);
	}
	
	@Operation(summary = "Apaga as preferências", description = "Método que apaga as preferências")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Preferências apagadas com sucesso"),
			@ApiResponse(responseCode = "400", description = "Parâmetros incorretos"),
			@ApiResponse(responseCode = "401", description = "Acesso não autorizado ou token inválido"),
			@ApiResponse(responseCode = "404", description = "Preferência não encontrada"),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor")
			})
	@DeleteMapping
	public ResponseEntity<Void> delete(@AuthenticationPrincipal Device device) {
		service.delete(device);
		return ResponseEntity.noContent().build();
	}

}
