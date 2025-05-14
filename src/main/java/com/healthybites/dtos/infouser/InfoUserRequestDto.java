package com.healthybites.dtos.infouser;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoUserRequestDto {

	@NotNull(message = "El campo altura no puede ser nulo")
	@Positive(message = "La altura debe ser un número positivo")
	private Double height;
	
	@NotNull(message = "El campo peso no puede ser nulo")
	@Positive(message = "El peso debe ser un número positivo")
	private Double weight;
	
	@NotBlank(message = "El nivel de actividad fisica del usario no puede estar en blanco")
	private String activityLevel;
	
	@NotNull(message = "El campo age no puede ser nulo")
	@Positive(message = "El age debe ser un número positivo")
	private int age;
	
	@NotBlank(message = "El nivel sexo no puede estar en blanco")
	private String sex;
}
