package com.healthybites.dtos.recipe;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * RecipeRequestDto
 * The request body containing the details of the recipe to be created.
 */
public class RecipeRequestDto {

    @NotBlank(message = "El nombre de la receta no puede estar en blanco")
    @Size(max = 80, message = "El nombre de la receta no puede tener más de 80 caracteres")
    private String name;

    @NotBlank(message = "La preparación no puede estar en blanco")
    @Size(max = 500, message = "La preparación no puede tener más de 500 caracteres")
    private String preparation;

    @NotNull(message = "El usuario no puede estar vacío")
    private Long userId;
    
    @NotNull(message = "La lista de ingredientes no puede ser nula")
    private List<RecipeIngredientDto> ingredients;
}
