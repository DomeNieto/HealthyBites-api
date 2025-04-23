package com.healthybites.service.recipe;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthybites.dtos.ingredient.IngredientResponseDto;
import com.healthybites.dtos.recipe.RecipeRequestDto;
import com.healthybites.dtos.recipe.RecipeResponseDto;
import com.healthybites.entity.IngredientEntity;
import com.healthybites.entity.RecipeEntity;
import com.healthybites.entity.RecipeIngredientEntity;
import com.healthybites.exception.ResourceNotFoundException;
import com.healthybites.mappers.ingredient.IngredientMapper;
import com.healthybites.mappers.recipe.RecipeMapper;
import com.healthybites.repositoy.IngredientRepository;
import com.healthybites.repositoy.RecipeIngredientRepository;
import com.healthybites.repositoy.RecipeRepository;

@Service
public class RecipeServiceImpl implements RecipeService {

	private final static String RECIPE_NOT_FOUND = "Recipe with id %d not found";
	private final static String INGREDIENT_NOT_FOUND = "Ingredient with id %d not found";

	@Autowired
	private RecipeIngredientRepository recipeIngredientRepository;

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private IngredientRepository ingredientRepository;

	@Autowired
	private RecipeMapper recipeMapper;

	@Autowired
	private IngredientMapper ingredientMapper;

	@Override
	public List<RecipeResponseDto> getAllRecipes() {
		return recipeRepository.findAll().stream().map(recipe -> {
			RecipeResponseDto recipeDto = recipeMapper.toRecipeResponseDto(recipe);
			List<IngredientResponseDto> ingredientes = getIngredientDtosByRecipe(recipe);
			recipeDto.setIngredients(ingredientes);
			return recipeDto;
		}).toList();
	}

	@Override
	public List<RecipeResponseDto> getAllRecipesByUser(Long userId) {
		return recipeRepository.findByUserId(userId).stream().map(recipe -> {
			RecipeResponseDto recipeDto = recipeMapper.toRecipeResponseDto(recipe);
			List<IngredientResponseDto> ingredientes = getIngredientDtosByRecipe(recipe);
			recipeDto.setIngredients(ingredientes);
			return recipeDto;
		}).toList();
	}
	
	@Override
	public RecipeResponseDto getRecipeById(Long recipeId) {
		RecipeEntity recipe = validateAndGetRecipe(recipeId);
		RecipeResponseDto response = recipeMapper.toRecipeResponseDto(recipe);
		response.setIngredients(getIngredientDtosByRecipe(recipe));
		return response;
	}

	@Override
	public RecipeResponseDto createRecipe(RecipeRequestDto recipeDto) {
		if (recipeRepository.existsByName(recipeDto.getName())) {
			throw new com.healthybites.exception.Exception(String.format("Ya existe una receta con el nombre %s", recipeDto.getName()));
		}
		RecipeEntity saved = recipeRepository.save(recipeMapper.toRecipe(recipeDto));

		if (recipeDto.getIngredients() != null) {
			recipeDto.getIngredients().forEach(recipeIngredientDto -> {
				IngredientEntity ingredient = validateAndGetIngredient(recipeIngredientDto.getIngredientId());
				RecipeIngredientEntity saveRelationRecipeIngredient = new RecipeIngredientEntity(saved, ingredient,
						recipeIngredientDto.getQuantity());
				
				recipeIngredientRepository.save(saveRelationRecipeIngredient);
			});
		}
		RecipeResponseDto response = recipeMapper.toRecipeResponseDto(saved);
		response.setIngredients(getIngredientDtosByRecipe(saved));
		return response;
	}

	@Override
	public List<IngredientResponseDto> getIngredientsForRecipe(Long recipeId) {
		validateAndGetRecipe(recipeId);
		return recipeIngredientRepository.findByRecipeId(recipeId).stream()
				.map(relation -> ingredientMapper.toIngredientResponseDto(relation.getIngredient())).toList();
	}

	@Override
	public void deleteRecipe(Long recipeId) {
		validateAndGetRecipe(recipeId);
		recipeIngredientRepository.deleteByRecipeId(recipeId);
		recipeRepository.deleteById(recipeId);
	}

	@Override
	public boolean deleteIngredientToRecipe(Long recipeId, Long ingredientId) {
		validateAndGetRecipe(recipeId);
		validateAndGetIngredient(ingredientId);
		recipeIngredientRepository.deleteByRecipeIdAndIngredientId(recipeId, ingredientId);
		return true;
	}

	@Override
	public RecipeResponseDto updateRecipe(Long recipeId, RecipeRequestDto dto) {
		RecipeEntity recipe = validateAndGetRecipe(recipeId);
		recipe.setName(dto.getName());
		recipe.setPreparation(dto.getPreparation());
		RecipeEntity updated = recipeRepository.save(recipe);

		recipeIngredientRepository.deleteByRecipeId(recipeId);
		if (dto.getIngredients() != null) {
			dto.getIngredients().forEach(recipeIngredientDto -> {
				IngredientEntity ingredient = validateAndGetIngredient(recipeIngredientDto.getIngredientId());
				RecipeIngredientEntity relation = new RecipeIngredientEntity(updated, ingredient,
						recipeIngredientDto.getQuantity());
				recipeIngredientRepository.save(relation);
			});
		}
		RecipeResponseDto response = recipeMapper.toRecipeResponseDto(updated);
		response.setIngredients(getIngredientDtosByRecipe(updated));
		return response;
	}

	// helpers

	private RecipeEntity validateAndGetRecipe(Long recipeId) {
		return recipeRepository.findById(recipeId)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(RECIPE_NOT_FOUND, recipeId)));
	}

	private IngredientEntity validateAndGetIngredient(Long ingredientId) {
		return ingredientRepository.findById(ingredientId)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(INGREDIENT_NOT_FOUND, ingredientId)));
	}
	
	private List<IngredientResponseDto> getIngredientDtosByRecipe(RecipeEntity recipe) {
	    List<RecipeIngredientEntity> relations = recipeIngredientRepository.findByRecipeId(recipe.getId());
	    return relations.stream().map(rel -> new IngredientResponseDto(
	            rel.getIngredient().getId(),
	            rel.getIngredient().getName(),
	            rel.getIngredient().getQuantityCalories(),
	            rel.getIngredient().getCreationDate()
	    )).toList();
	}


}
