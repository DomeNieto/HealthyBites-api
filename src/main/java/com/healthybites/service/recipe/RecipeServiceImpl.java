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
import com.healthybites.entity.UserEntity;
import com.healthybites.exception.ResourceNotFoundException;
import com.healthybites.mappers.ingredient.IngredientMapper;
import com.healthybites.mappers.recipe.RecipeMapper;
import com.healthybites.repositoy.IngredientRepository;
import com.healthybites.repositoy.RecipeIngredientRepository;
import com.healthybites.repositoy.RecipeRepository;
import com.healthybites.repositoy.UserRepository;

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
	private UserRepository userRepository;

	@Autowired
	private RecipeMapper recipeMapper;

	@Autowired
	private IngredientMapper ingredientMapper;

	/**
	 * This method retrieves all recipes from the database and maps them to a list of
	 * RecipeResponseDto objects.
	 * 
	 * @return List<RecipeResponseDto> - A list of all recipes in the system.
	 */
	@Override
	public List<RecipeResponseDto> getAllRecipes() {
		return recipeRepository.findAll().stream().map(recipe -> {
			RecipeResponseDto recipeDto = recipeMapper.toRecipeResponseDto(recipe);
			List<IngredientResponseDto> ingredientes = getIngredientDtosByRecipe(recipe);
			recipeDto.setIngredients(ingredientes);
			return recipeDto;
		}).toList();
	}

	/**
	 * This method retrieves all recipes for a specific user from the database and maps them to a list
	 * of RecipeResponseDto objects.
	 * 
	 * @param userId - The ID of the user whose recipes are to be retrieved.
	 * @return List<RecipeResponseDto> - A list of recipes for the specified user.
	 */
	@Override
	public List<RecipeResponseDto> getAllRecipesByUser(Long userId) {
		return recipeRepository.findByUserId(userId).stream().map(recipe -> {
			RecipeResponseDto recipeDto = recipeMapper.toRecipeResponseDto(recipe);
			List<IngredientResponseDto> ingredientes = getIngredientDtosByRecipe(recipe);
			recipeDto.setIngredients(ingredientes);
			return recipeDto;
		}).toList();
	}
	
	/**
	 * This method retrieves a recipe by its ID from the database and maps it to a RecipeResponseDto
	 * object.
	 * 
	 * @param recipeId - The ID of the recipe to be retrieved.
	 * @return RecipeResponseDto - The recipe with the specified ID.
	 */
	@Override
	public RecipeResponseDto getRecipeById(Long recipeId) {
		RecipeEntity recipe = validateAndGetRecipe(recipeId);
		RecipeResponseDto response = recipeMapper.toRecipeResponseDto(recipe);
		response.setIngredients(getIngredientDtosByRecipe(recipe));
		return response;
	}

	/**
	 * This method creates a new recipe in the database and maps it to a RecipeResponseDto object.
	 * 
	 * @param recipeDto - The request DTO containing the details of the recipe to be created.
	 * @return RecipeResponseDto - The created recipe as a response DTO.
	 */
	@Override
	public RecipeResponseDto createRecipe(RecipeRequestDto recipeDto) {
		if (recipeRepository.existsByName(recipeDto.getName())) {
			throw new com.healthybites.exception.Exception(String.format("Ya existe una receta con el nombre %s", recipeDto.getName()));
		}
		UserEntity user = validateAndGetUser(recipeDto.getUserId());
		RecipeEntity toSave = recipeMapper.toRecipe(recipeDto);
		toSave.setUser(user);
		RecipeEntity saved = recipeRepository.save(toSave);
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

	/**
	 * This method retrieves all ingredients for a specific recipe from the database and maps them to
	 * a list of IngredientResponseDto objects.
	 * 
	 * @param recipeId - The ID of the recipe whose ingredients are to be retrieved.
	 * @return List<IngredientResponseDto> - A list of ingredients for the specified recipe.
	 */
	@Override
	public List<IngredientResponseDto> getIngredientsForRecipe(Long recipeId) {
		validateAndGetRecipe(recipeId);
		return recipeIngredientRepository.findByRecipeId(recipeId).stream()
				.map(relation -> ingredientMapper.toIngredientResponseDto(relation.getIngredient())).toList();
	}

	/**
	 * This method deletes a recipe by its ID from the database.
	 * 
	 * @param recipeId - The ID of the recipe to be deleted.
	 */
	@Override
	public void deleteRecipe(Long recipeId) {
		validateAndGetRecipe(recipeId);
		recipeIngredientRepository.deleteByRecipeId(recipeId);
		recipeRepository.deleteById(recipeId);
	}

	/**
	 * This method updates an existing recipe in the database and maps it to a RecipeResponseDto object.
	 * 
	 * @param recipeId   - The ID of the recipe to be updated.
	 * @param dto        - The request DTO containing the updated details of the recipe.
	 * @return RecipeResponseDto - The updated recipe as a response DTO.
	 */
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

	/**
	 * This method adds an ingredient to a recipe by creating a new relation in the database.
	 * 
	 * @param recipeId    - The ID of the recipe to which the ingredient is to be added.
	 * @param ingredientId - The ID of the ingredient to be added.
	 * @param quantity     - The quantity of the ingredient to be added.
	 * @return boolean - true if the ingredient was added successfully, false otherwise.
	 */
	@Override
	public boolean addIngredientToRecipe(Long recipeId, Long ingredientId, float quantity) {
		try {
			RecipeEntity recipe = validateAndGetRecipe(recipeId);
			IngredientEntity ingredient = validateAndGetIngredient(ingredientId);
			RecipeIngredientEntity relation = new RecipeIngredientEntity(recipe, ingredient, quantity);
			recipeIngredientRepository.save(relation);
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// helpers

	/**
	 * This method validates and retrieves a user by its ID from the database.
	 * 
	 * @param userId - The ID of the user to be retrieved.
	 * @return UserEntity - The user with the specified ID.
	 */
	private UserEntity validateAndGetUser(Long userId) {
	    return userRepository.findById(userId)
	        .orElseThrow(() -> new ResourceNotFoundException(
	            String.format("User with id %d not found", userId)));
	}
	/**
	 * This method validates and retrieves a recipe by its ID from the database.
	 * 
	 * @param recipeId - The ID of the recipe to be retrieved.
	 * @return RecipeEntity - The recipe with the specified ID.
	 */
	private RecipeEntity validateAndGetRecipe(Long recipeId) {
		return recipeRepository.findById(recipeId)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(RECIPE_NOT_FOUND, recipeId)));
	}

	/**
	 * This method validates and retrieves an ingredient by its ID from the database.
	 * 
	 * @param ingredientId - The ID of the ingredient to be retrieved.
	 * @return IngredientEntity - The ingredient with the specified ID.
	 */
	private IngredientEntity validateAndGetIngredient(Long ingredientId) {
		return ingredientRepository.findById(ingredientId)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(INGREDIENT_NOT_FOUND, ingredientId)));
	}
	
	/**
	 * This method retrieves all ingredients for a specific recipe from the database and maps them to
	 * a list of IngredientResponseDto objects.
	 * 
	 * @param recipe - The recipe whose ingredients are to be retrieved.
	 * @return List<IngredientResponseDto> - A list of ingredients for the specified recipe.
	 */
	private List<IngredientResponseDto> getIngredientDtosByRecipe(RecipeEntity recipe) {
	    List<RecipeIngredientEntity> relations = recipeIngredientRepository.findByRecipeId(recipe.getId());
	    return relations.stream().map(rel -> {
	        float quantity  = rel.getQuantity();                          
	        float calories = rel.getIngredient().getQuantityCalories();  
	        float total  = quantity * calories;                      

	        return new IngredientResponseDto(
	            rel.getIngredient().getId(),
	            rel.getIngredient().getName(),
	            quantity,         
	            total,
	            rel.getIngredient().isActive(),
	            rel.getIngredient().getCreationDate()
				
	        );
	    }).toList();
	}



}
