package com.healthybites.service.ingredient;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthybites.dtos.ingredient.IngredientRequestDto;
import com.healthybites.dtos.ingredient.IngredientResponseDto;
import com.healthybites.entity.IngredientEntity;
import com.healthybites.entity.RecipeIngredientEntity;
import com.healthybites.exception.ResourceNotFoundException;
import com.healthybites.mappers.ingredient.IngredientMapper;
import com.healthybites.repositoy.IngredientRepository;
import com.healthybites.repositoy.RecipeIngredientRepository;
import com.healthybites.repositoy.RecipeRepository;

@Service
public class IngredientServiceImpl implements IngredientService {

	private final static String RECIPE_NOT_FOUND = "Recipe with id %d not found";
	private final static String INGREDIENT_NOT_FOUND = "Ingredient with id %d not found";

	@Autowired
	private RecipeIngredientRepository recipeIngredientRepository;

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private IngredientRepository ingredientRepository;

	@Autowired
	private IngredientMapper ingredientMapper;

	@Override
	public List<IngredientResponseDto> getAllIngredients() {
		return ingredientRepository.findAll().stream().map(ingredientMapper::toIngredientResponseDto).toList();
	}

	@Override
	public IngredientResponseDto getIngredientById(Long ingredientId) {
		IngredientEntity ingredient = validateAndGetIngredient(ingredientId);
		return ingredientMapper.toIngredientResponseDto(ingredient);
	}

	@Override
	public List<IngredientResponseDto> getAllgetAllIngredientsByRecipe(Long recipeId) {
		recipeRepository.findById(recipeId)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(RECIPE_NOT_FOUND, recipeId)));

		List<RecipeIngredientEntity> relations = recipeIngredientRepository.findByRecipeId(recipeId);

		return relations.stream()
				.map(recipeIngredient -> ingredientMapper.toIngredientResponseDto(recipeIngredient.getIngredient()))
				.toList();
	}

	@Override
	public IngredientResponseDto createIngredient(IngredientRequestDto ingredientRequestDto) {
		IngredientEntity entity = ingredientMapper.toIngredient(ingredientRequestDto);
		return ingredientMapper.toIngredientResponseDto(ingredientRepository.save(entity));
	}

	@Override
	public IngredientResponseDto updateIngredient(Long ingredientId, IngredientRequestDto ingredientRequest) {

		IngredientEntity ingredient = validateAndGetIngredient(ingredientId);

		ingredient.setName(ingredientRequest.getName());
		ingredient.setQuantityCalories(ingredientRequest.getQuantityCalories());
		ingredient.setCreationDate(ingredientRequest.getCreationDate());

		return ingredientMapper.toIngredientResponseDto(ingredientRepository.save(ingredient));
	}

	@Override
	public void deleteIngredient(Long ingredientId) {
		IngredientEntity ingredient = validateAndGetIngredient(ingredientId);
		recipeIngredientRepository.deleteByIngredientId(ingredient.getId());
		ingredientRepository.delete(ingredient);
	}

	// helpers

	private IngredientEntity validateAndGetIngredient(Long ingredientId) {
		return ingredientRepository.findById(ingredientId)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(INGREDIENT_NOT_FOUND, ingredientId)));
	}
	
}
