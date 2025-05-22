package com.healthybites.dtos.recipe;

import java.util.List;

import com.healthybites.dtos.ingredient.IngredientResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * RecipeResponseDto
 * The response body containing the details of the recipe.
 */
public class RecipeResponseDto {
	private long id;
    private String name;
    private String preparation;
    private List<IngredientResponseDto> ingredients;
}
