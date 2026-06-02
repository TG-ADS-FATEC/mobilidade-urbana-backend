package com.sptrans.mobilidade_urbana.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;

public class ProfileDTO {
	
	@Schema(description="E-mail do usuário", example="joaodasilva@yahoo.com")
	@Email(message = "Email inválido")
	private String email;
	@Schema(description = "Data de criação do perfil", example = "2026-05-07T22:09:49.22619787")
	private LocalDateTime createdAt;
	@Schema(description = "Data de atualização da perfil", example = "2026-05-07T22:09:49.22619787")
	private LocalDateTime updatedAt;
	
	public ProfileDTO() {}

	public ProfileDTO(@Email(message = "Email inválido") String email, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		super();
		this.email = email;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	

}
