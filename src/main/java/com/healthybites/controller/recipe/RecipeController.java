package com.healthybites.controller.recipe;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthybites.api.ApiResponseDto;
import com.healthybites.dtos.ingredient.IngredientResponseDto;
import com.healthybites.dtos.recipe.AddIngredientToRecipeDto;
import com.healthybites.dtos.recipe.RecipeRequestDto;
import com.healthybites.dtos.recipe.RecipeResponseDto;
import com.healthybites.service.recipe.RecipeServiceImpl;

import org.springframework.http.*;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class RecipeController {

    private static final String RECIPE_RESOURCE = "/recipes";
    private static final String RECIPE_ID_PATH = RECIPE_RESOURCE + "/{recipeId}";

    @Autowired
    private RecipeServiceImpl recipeService;

    /**
     * This Java function retrieves all recipes by user and returns them in a JSON response.
     * @param userId 
     * @return The method `getAllRecipesByUser` returns a `ResponseEntity` containing an
     * `ApiResponseDto` with a list of `RecipeResponseDto` objects representing all recipes fetched
     * for a specific user. The response message is "Recipes fetched for user successfully" with an
     * HTTP status code of 200 (OK).
     */
    @GetMapping(value = RECIPE_RESOURCE + "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto<List<RecipeResponseDto>>> getAllRecipesByUser(@PathVariable Long userId) {
        List<RecipeResponseDto> recipes = recipeService.getAllRecipesByUser(userId);
        ApiResponseDto<List<RecipeResponseDto>> response =
                new ApiResponseDto<>("Recipes fetched for user successfully", HttpStatus.OK.value(), recipes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    /**
     * This Java function retrieves one recipes by id and returns them in a JSON response.
     * 
     * @return The method `getAllRecipes()` returns a ResponseEntity object containing an
     * ApiResponseDto with a list of RecipeResponseDto objects. This response entity is returned
     * with an HTTP status code of 200 (OK).
     */
    @GetMapping(value = RECIPE_ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto<RecipeResponseDto>> getRecipeById(@PathVariable Long recipeId) {
    	RecipeResponseDto recipe = recipeService.getRecipeById(recipeId);
        ApiResponseDto<RecipeResponseDto> response =
                new ApiResponseDto<>("Recipe fetched successfully", HttpStatus.OK.value(), recipe);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This Java function creates a new recipe by processing a POST request with JSON data and
     * returns a response with the created recipe details.
     * 
     * @return The method `createRecipe()` returns a `ResponseEntity` containing an
     * `ApiResponseDto` with the created `RecipeResponseDto` object. The response message is "Recipe
     * created successfully" with an HTTP status code of 201 (Created).
     */
    @PostMapping(value = RECIPE_RESOURCE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto<RecipeResponseDto>> createRecipe(@Valid @RequestBody RecipeRequestDto recipeRequestDto) {
        RecipeResponseDto createdRecipe = recipeService.createRecipe(recipeRequestDto);
        System.out.println("Recipe Request DTO: " + recipeRequestDto);
        ApiResponseDto<RecipeResponseDto> response =
                new ApiResponseDto<>("Recipe created successfully", HttpStatus.CREATED.value(), createdRecipe);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * This Java function retrieves all ingredients for a specific recipe and returns them in a JSON
     * response.
     * 
     * @return The method `getIngredientsForRecipe()` returns a `ResponseEntity` containing an
     * `ApiResponseDto` with a list of `IngredientResponseDto` objects representing all ingredients
     * fetched for a specific recipe. The response message is "Ingredients for recipe fetched
     * successfully" with an HTTP status code of 200 (OK).
     */
    @GetMapping(value = RECIPE_ID_PATH + "/ingredients", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto<List<IngredientResponseDto>>> getIngredientsForRecipe(@PathVariable Long recipeId) {
        List<IngredientResponseDto> ingredients = recipeService.getIngredientsForRecipe(recipeId);
        ApiResponseDto<List<IngredientResponseDto>> response =
                new ApiResponseDto<>("Ingredients for recipe fetched successfully", HttpStatus.OK.value(), ingredients);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This Java function updates a recipe by processing a PUT request with JSON data and returns a
     * response with the updated recipe details.
     * 
     * @return The method `updateRecipe()` returns a `ResponseEntity` containing an
     * `ApiResponseDto` with the updated `RecipeResponseDto` object. The response message is "Recipe
     * updated successfully" with an HTTP status code of 200 (OK).
     */
    @PutMapping(value = RECIPE_ID_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto<RecipeResponseDto>> updateRecipe(
            @PathVariable Long recipeId, @Valid @RequestBody RecipeRequestDto recipeRequestDto) {
        RecipeResponseDto updatedRecipe = recipeService.updateRecipe(recipeId, recipeRequestDto);
        ApiResponseDto<RecipeResponseDto> response =
                new ApiResponseDto<>("Recipe updated successfully", HttpStatus.OK.value(), updatedRecipe);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This Java function deletes a recipe by its ID and returns a response indicating the deletion
     * status.
     * 
     * @return The method `deleteRecipe()` returns a `ResponseEntity` with a status of NO_CONTENT
     * (204) indicating that the recipe was successfully deleted.
     */
    @DeleteMapping(value = RECIPE_ID_PATH)
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long recipeId) {
        recipeService.deleteRecipe(recipeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * This Java function adds an ingredient to a recipe by processing a POST request with JSON data
     * and returns a response indicating the success of the operation.
     * 
     * @return The method `addIngredientToRecipe()` returns a `ResponseEntity` containing an
     * `ApiResponseDto` with a success message indicating that the ingredient was added to the recipe
     * successfully. The HTTP status code is 200 (OK).
     */
    @PostMapping(value = RECIPE_ID_PATH + "/ingredients", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto<String>> addIngredientToRecipe(
            @PathVariable Long recipeId,
            @Valid @RequestBody AddIngredientToRecipeDto dto) {

        recipeService.addIngredientToRecipe(recipeId, dto.getIngredientId(), dto.getQuantity());

        ApiResponseDto<String> response = new ApiResponseDto<>(
                "Ingredient added to recipe successfully", HttpStatus.OK.value(), null);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
