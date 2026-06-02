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

import com.sptrans.mobilidade_urbana.dto.ProfileDTO;
import com.sptrans.mobilidade_urbana.entities.Device;
import com.sptrans.mobilidade_urbana.security.SecurityConfiguration;
import com.sptrans.mobilidade_urbana.services.ProfileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value="/profiles")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class ProfileController {
	
	@Autowired
	private ProfileService service;
	
	@Operation(summary = "Busca o perfil do usuário", description = "Método que retorna o perfil do usuário")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Perfil retornado com sucesso"),
			@ApiResponse(responseCode = "401", description = "Acesso não autorizado ou token inválido"),
			@ApiResponse(responseCode = "404", description = "Perfil não encontrado"),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor")
			})
	@GetMapping
	public ResponseEntity<ProfileDTO> findMyProfile(@AuthenticationPrincipal Device device){
		ProfileDTO dto = service.findByDeviceId(device.getDeviceId());
		return ResponseEntity.ok(dto);
	}
	
	@Operation(summary = "Insere um perfil", description = "Método que insere um perfil")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Perfil criado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Parâmetros incorretos"),
			@ApiResponse(responseCode = "401", description = "Acesso não autorizado ou token inválido"),
			@ApiResponse(responseCode = "409", description = "Perfil já existente"),
			@ApiResponse(responseCode = "422", description = "E-mail inválido"),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor")
			})
	@PostMapping
	public ResponseEntity<ProfileDTO> insert(@AuthenticationPrincipal Device device, @Valid @RequestBody ProfileDTO dto) {
		dto = service.insert(device, dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@Operation(summary = "Atualiza um perfil", description = "Método que atualiza um perfil de usuário")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Perfil atualizado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Parâmetros incorretos"),
			@ApiResponse(responseCode = "401", description = "Acesso não autorizado ou token inválido"),
			@ApiResponse(responseCode = "404", description = "Perfil não encontrado"),
			@ApiResponse(responseCode = "422", description = "E-mail inválido"),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor")
			})
	@PutMapping
	public ResponseEntity<ProfileDTO> update(@AuthenticationPrincipal Device device, @Valid @RequestBody ProfileDTO dto) {
		dto = service.updateByDeviceId(device.getDeviceId(), dto);
		return ResponseEntity.ok(dto);
	}
	
	@Operation(summary = "Exclui um perfil", description = "Método que exclui um perfil")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Perfil excluido com sucesso"),
			@ApiResponse(responseCode = "401", description = "Acesso não autorizado ou token inválido"),
			@ApiResponse(responseCode = "404", description = "Perfil não encontrado"),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor")
			})
	@DeleteMapping
	public ResponseEntity<Void> delete(@AuthenticationPrincipal Device device) {
		service.deleteByDeviceId(device.getDeviceId());
		return ResponseEntity.noContent().build();
	}
	

}
