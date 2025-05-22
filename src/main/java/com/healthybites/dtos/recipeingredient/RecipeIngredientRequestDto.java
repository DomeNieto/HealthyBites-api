package com.healthybites.dtos.recipeingredient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * RecipeIngredientRequestDto
 * The request body containing the details of the ingredient to be added to the recipe.
 */
public class RecipeIngredientRequestDto {
    private Long ingredientId;
    private float quantity;
}
