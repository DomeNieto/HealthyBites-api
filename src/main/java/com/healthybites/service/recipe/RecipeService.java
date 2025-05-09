package com.healthybites.service.recipe;

import java.util.List;

import com.healthybites.dtos.ingredient.IngredientResponseDto;
import com.healthybites.dtos.recipe.RecipeRequestDto;
import com.healthybites.dtos.recipe.RecipeResponseDto;

public interface RecipeService {

	public List<RecipeResponseDto> getAllRecipes();
	public RecipeResponseDto createRecipe(RecipeRequestDto recipeRequestDto);
	public RecipeResponseDto updateRecipe(Long recipeId, RecipeRequestDto recipeRequest);
	public void deleteRecipe(Long recipeId);
	public boolean deleteIngredientToRecipe(Long recipeId, Long ingredientId);
	public List<IngredientResponseDto> getIngredientsForRecipe(Long recipeId);
	public List<RecipeResponseDto> getAllRecipesByUser(Long userId);
	public RecipeResponseDto getRecipeById(Long recipeId);
	public boolean addIngredientToRecipe(Long recipeId, Long ingredientId, float quantity);
}
