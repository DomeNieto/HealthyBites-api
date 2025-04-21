package com.healthybites.dtos.ingredient;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientRequestDto {

    @NotBlank(message = "El nombre del ingrediente no puede estar en blanco")
    @Size(max = 60, message = "El nombre del ingrediente no puede tener más de 60 caracteres")
    private String name;

    @Positive(message = "Las calorías deben ser un número positivo")
    private float quantityCalories;

    @NotNull(message = "La fecha de creación no puede ser nula")
    private LocalDateTime creationDate;
}