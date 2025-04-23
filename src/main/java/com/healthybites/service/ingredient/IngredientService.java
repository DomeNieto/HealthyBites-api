package com.healthybites.service.ingredient;

import java.util.List;

import com.healthybites.dtos.ingredient.IngredientRequestDto;
import com.healthybites.dtos.ingredient.IngredientResponseDto;

public interface IngredientService {

	public List<IngredientResponseDto> getAllIngredients();
	public List<IngredientResponseDto> getAllgetAllIngredientsByRecipe(Long recipeId);
	public IngredientResponseDto createIngredient(IngredientRequestDto ingredientRequestDto);
	public IngredientResponseDto updateIngredient(Long ingredientId, IngredientRequestDto ingredientRequest);
	public void deleteIngredient(Long ingredientId);
	public IngredientResponseDto getIngredientById(Long ingredientId);

}
