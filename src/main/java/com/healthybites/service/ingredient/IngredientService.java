package com.healthybites.service.ingredient;

import java.util.List;

import com.healthybites.dtos.ingredient.IngredientRequestDto;
import com.healthybites.dtos.ingredient.IngredientResponseDto;
//  Service interface for managing ingredient operations.
public interface IngredientService {

	List<IngredientResponseDto> getAllIngredients();
	IngredientResponseDto createIngredient(IngredientRequestDto ingredientRequestDto);
	IngredientResponseDto updateIngredient(Long ingredientId, IngredientRequestDto ingredientRequest);
	void disableIngredient(Long ingredientId);
	List<IngredientResponseDto> getAllActiveIngredients();
	IngredientResponseDto reactivateIngredient(Long ingredientId);

}
