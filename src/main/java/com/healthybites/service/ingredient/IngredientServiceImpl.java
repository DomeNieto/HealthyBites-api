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

	/**
	 * This method retrieves all ingredients from the database and maps them to a list of
	 * IngredientResponseDto objects.
	 * 
	 * @return List<IngredientResponseDto> - A list of all ingredients in the system.
	 */
	@Override
	public List<IngredientResponseDto> getAllIngredients() {
		return ingredientRepository.findAll().stream().map(ingredientMapper::toIngredientResponseDto).toList();
	}

	/**
	 * This method creates a new ingredient in the database and returns the created ingredient as a
	 * response DTO.
	 * 
	 * @param ingredientRequestDto - The request DTO containing the details of the ingredient to be
	 *                              created.
	 * @return IngredientResponseDto - The created ingredient as a response DTO.
	 */
	@Override
	public IngredientResponseDto createIngredient(IngredientRequestDto ingredientRequestDto) {
		IngredientEntity entity = ingredientMapper.toIngredient(ingredientRequestDto);
		entity.setActive(true); 
		return ingredientMapper.toIngredientResponseDto(ingredientRepository.save(entity));
	}

	/**
	 * This method updates an existing ingredient in the database and returns the updated ingredient
	 * as a response DTO.
	 * 
	 * @param ingredientId        - The ID of the ingredient to be updated.
	 * @param ingredientRequest   - The request DTO containing the updated details of the ingredient.
	 * @return IngredientResponseDto - The updated ingredient as a response DTO.
	 */
	@Override
	public IngredientResponseDto updateIngredient(Long ingredientId, IngredientRequestDto ingredientRequest) {

		IngredientEntity ingredient = validateAndGetIngredient(ingredientId);

		ingredient.setName(ingredientRequest.getName());
		ingredient.setQuantityCalories(ingredientRequest.getQuantityCalories());
		ingredient.setCreationDate(ingredientRequest.getCreationDate());

		return ingredientMapper.toIngredientResponseDto(ingredientRepository.save(ingredient));
	}

	/**
	 * This method disables an ingredient by setting its active status to false.
	 * 
	 * @param ingredientId - The ID of the ingredient to be disabled.
	 */
	@Override
	public void disableIngredient(Long ingredientId) {
		IngredientEntity ingredient = validateAndGetIngredient(ingredientId);
		ingredient.setActive(false);
	    ingredientRepository.save(ingredient);
	}
	
	/**
	 * This method retrieves all active ingredients from the database and maps them to a list of
	 * IngredientResponseDto objects.
	 * 
	 * @return List<IngredientResponseDto> - A list of all active ingredients in the system.
	 */
	@Override
	public List<IngredientResponseDto> getAllActiveIngredients() {
		return ingredientRepository.findByActiveTrue().stream().map(ingredientMapper::toIngredientResponseDto).toList();
	}
	
	/**
	 * This method reactivates an ingredient by setting its active status to true.
	 * 
	 * @param ingredientId - The ID of the ingredient to be reactivated.
	 * @return IngredientResponseDto - The reactivated ingredient as a response DTO.
	 */
	@Override
	public IngredientResponseDto reactivateIngredient(Long ingredientId) {
		IngredientEntity ingredient = validateAndGetIngredient(ingredientId);
		ingredient.setActive(true);
		return ingredientMapper.toIngredientResponseDto(ingredientRepository.save(ingredient));
	}

	// helpers

	/**
	 * This method validates and retrieves an ingredient by its ID.
	 * 
	 * @param ingredientId - The ID of the ingredient to be retrieved.
	 * @return IngredientEntity - The ingredient entity if found.
	 * @throws ResourceNotFoundException - If the ingredient with the given ID is not found.
	 */
	private IngredientEntity validateAndGetIngredient(Long ingredientId) {
		return ingredientRepository.findById(ingredientId)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(INGREDIENT_NOT_FOUND, ingredientId)));
	}

	
}
