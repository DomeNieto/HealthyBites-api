package com.healthybites.dtos.user;

import com.healthybites.dtos.infouser.InfoUserRequestDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntityRequestDto {
	
	@NotBlank(message = "El nombre no puede estar en blanco")
    @Size(max = 25, message = "El nombre no puede tener más de 25 caracteres")
	private String name;
	
    @NotBlank(message = "El email no puede estar en blanco")
    @Email(message = "El email debe ser válido")
    @Size(max = 30, message = "El email no puede tener más de 30 caracteres")
	private String email;
	
    @NotBlank(message = "La contraseña no puede estar en blanco")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres") 
	private String password;
  
    @Valid
    private InfoUserRequestDto infoUser;
}
