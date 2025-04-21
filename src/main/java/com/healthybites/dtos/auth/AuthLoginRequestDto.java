package com.healthybites.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthLoginRequestDto {
	
	@NotBlank(message = "El email del usario no puede estar en blanco")
	private String email;
	
	@NotBlank(message = "La contraseña del usario no puede estar en blanco")
	private String password;
}

