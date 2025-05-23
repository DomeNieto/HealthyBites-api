package com.healthybites.service.recipe;

import java.util.List;

import com.healthybites.dtos.ingredient.IngredientResponseDto;
import com.healthybites.dtos.recipe.RecipeRequestDto;
import com.healthybites.dtos.recipe.RecipeResponseDto;
//  Service interface for managing recipe operations.
public interface RecipeService {

	List<RecipeResponseDto> getAllRecipes();
	RecipeResponseDto createRecipe(RecipeRequestDto recipeRequestDto);
	RecipeResponseDto updateRecipe(Long recipeId, RecipeRequestDto recipeRequest);
	void deleteRecipe(Long recipeId);
	List<IngredientResponseDto> getIngredientsForRecipe(Long recipeId);
	List<RecipeResponseDto> getAllRecipesByUser(Long userId);
	RecipeResponseDto getRecipeById(Long recipeId);
	boolean addIngredientToRecipe(Long recipeId, Long ingredientId, float quantity);
}
