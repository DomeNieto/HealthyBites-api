package com.healthybites.dtos.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleRequestDto {
	
    @NotBlank(message = "El nombre del rol no puede estar en blanco")
    @Size(max = 20, message = "El nombre del rol no puede tener más de 20 caracteres")
	private String name;
    
}
