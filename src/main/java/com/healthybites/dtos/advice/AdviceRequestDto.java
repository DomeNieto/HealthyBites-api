package com.healthybites.dtos.advice;


import java.time.LocalDateTime;

import com.healthybites.entity.UserEntity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdviceRequestDto {

	@NotBlank(message = "El título del consejo no puede estar en blanco")
    @Size(max = 120, message = "El título del consejo no puede tener más de 120 caracteres")
	private String title;
	@NotBlank(message = "La descripción no puede estar en blanco")
    @Size(max = 200, message = "La descripción no puede tener más de 200 caracteres")
	private String description;
	@NotBlank(message = "El campo de fecha de creación no puede estar en blanco")
	private LocalDateTime creationDate;
	@NotBlank(message = "El campo usuario no puede estar en blanco")
    private UserEntity user;
}
