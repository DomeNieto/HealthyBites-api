package com.healthybites.dtos.recipe;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * AddIngredientToRecipeDto
 * The request body containing the details of the ingredient to be added to the recipe.
 */
public class AddIngredientToRecipeDto {
	@NotNull(message = "El ID del ingrediente no puede ser nulo")
    private Long ingredientId;

    @Positive(message = "La cantidad debe ser un número positivo")
    private float quantity;
}
