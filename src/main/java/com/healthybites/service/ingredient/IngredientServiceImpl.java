package com.healthybites.service.ingredient;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthybites.dtos.ingredient.IngredientRequestDto;
import com.healthybites.dtos.ingredient.IngredientResponseDto;
import com.healthybites.entity.IngredientEntity;
import com.healthybites.exception.ResourceNotFoundException;
import com.healthybites.mappers.ingredient.IngredientMapper;
import com.healthybites.repositoy.IngredientRepository;


@Service
public class IngredientServiceImpl implements IngredientService {

	private final static String INGREDIENT_NOT_FOUND = "Ingredient with id %d not found";

	@Autowired
	private IngredientRepository ingredientRepository;

	@Autowired
	private IngredientMapper ingredientMapper;

	@Override
	public List<IngredientResponseDto> getAllIngredients() {
		return ingredientRepository.findAll().stream().map(ingredientMapper::toIngredientResponseDto).toList();
	}

	@Override
	public IngredientResponseDto createIngredient(IngredientRequestDto ingredientRequestDto) {
		IngredientEntity entity = ingredientMapper.toIngredient(ingredientRequestDto);
		entity.setActive(true); 
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
	public void disableIngredient(Long ingredientId) {
		IngredientEntity ingredient = validateAndGetIngredient(ingredientId);
		ingredient.setActive(false);
	    ingredientRepository.save(ingredient);
	}
	
	@Override
	public List<IngredientResponseDto> getAllActiveIngredients() {
		return ingredientRepository.findByActiveTrue().stream().map(ingredientMapper::toIngredientResponseDto).toList();
	}
	
	@Override
	public IngredientResponseDto reactivateIngredient(Long ingredientId) {
		IngredientEntity ingredient = validateAndGetIngredient(ingredientId);
		ingredient.setActive(true);
		return ingredientMapper.toIngredientResponseDto(ingredientRepository.save(ingredient));
	}

	// helpers
	private IngredientEntity validateAndGetIngredient(Long ingredientId) {
		return ingredientRepository.findById(ingredientId)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(INGREDIENT_NOT_FOUND, ingredientId)));
	}

	
}
