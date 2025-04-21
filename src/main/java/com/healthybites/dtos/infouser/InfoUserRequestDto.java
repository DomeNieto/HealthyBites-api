package com.healthybites.dtos.infouser;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoUserRequestDto {

	@NotBlank(message = "La altura del usario no puede estar en blanco")
	private Double height;
	
	@NotBlank(message = "El peso del usariopuede no puede estar en blanco")
	private Double weight;
	
	@NotBlank(message = "El nivel de actividad fisica del usario no puede estar en blanco")
	private String activityLevel;
}
