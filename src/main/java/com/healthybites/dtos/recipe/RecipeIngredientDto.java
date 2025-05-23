package com.healthybites.dtos.recipe;



import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * RecipeIngredientDto
 * The request body containing the details of the ingredient to be added to the recipe.
 */
public class RecipeIngredientDto {
	@NotNull(message = "El id del ingrediente para la receta no puede estar vacío")
	private Long ingredientId;

	@NotNull(message = "La cantidad de uso del ingrediente para la receta no puede estar vacía")
	private float quantity;

}
