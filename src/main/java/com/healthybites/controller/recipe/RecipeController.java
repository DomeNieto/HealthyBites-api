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

    @GetMapping(value = RECIPE_RESOURCE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto<List<RecipeResponseDto>>> getAllRecipes() {
        List<RecipeResponseDto> recipes = recipeService.getAllRecipes();
        ApiResponseDto<List<RecipeResponseDto>> response =
                new ApiResponseDto<>("Recipes fetched successfully", HttpStatus.OK.value(), recipes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = RECIPE_RESOURCE + "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto<List<RecipeResponseDto>>> getAllRecipesByUser(@PathVariable Long userId) {
        List<RecipeResponseDto> recipes = recipeService.getAllRecipesByUser(userId);
        ApiResponseDto<List<RecipeResponseDto>> response =
                new ApiResponseDto<>("Recipes fetched for user successfully", HttpStatus.OK.value(), recipes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping(value = RECIPE_ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto<RecipeResponseDto>> getRecipeById(@PathVariable Long recipeId) {
    	RecipeResponseDto recipe = recipeService.getRecipeById(recipeId);
        ApiResponseDto<RecipeResponseDto> response =
                new ApiResponseDto<>("Recipe fetched successfully", HttpStatus.OK.value(), recipe);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = RECIPE_RESOURCE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto<RecipeResponseDto>> createRecipe(@Valid @RequestBody RecipeRequestDto recipeRequestDto) {
        RecipeResponseDto createdRecipe = recipeService.createRecipe(recipeRequestDto);
        System.out.println("Recipe Request DTO: " + recipeRequestDto);
        ApiResponseDto<RecipeResponseDto> response =
                new ApiResponseDto<>("Recipe created successfully", HttpStatus.CREATED.value(), createdRecipe);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(value = RECIPE_ID_PATH + "/ingredients", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto<List<IngredientResponseDto>>> getIngredientsForRecipe(@PathVariable Long recipeId) {
        List<IngredientResponseDto> ingredients = recipeService.getIngredientsForRecipe(recipeId);
        ApiResponseDto<List<IngredientResponseDto>> response =
                new ApiResponseDto<>("Ingredients for recipe fetched successfully", HttpStatus.OK.value(), ingredients);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = RECIPE_ID_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto<RecipeResponseDto>> updateRecipe(
            @PathVariable Long recipeId, @Valid @RequestBody RecipeRequestDto recipeRequestDto) {
        RecipeResponseDto updatedRecipe = recipeService.updateRecipe(recipeId, recipeRequestDto);
        ApiResponseDto<RecipeResponseDto> response =
                new ApiResponseDto<>("Recipe updated successfully", HttpStatus.OK.value(), updatedRecipe);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = RECIPE_ID_PATH)
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long recipeId) {
        recipeService.deleteRecipe(recipeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

 
    @DeleteMapping(value = RECIPE_ID_PATH + "/ingredients/{ingredientId}")
    public ResponseEntity<Void> deleteIngredientFromRecipe(@PathVariable Long recipeId, @PathVariable Long ingredientId) {
        recipeService.deleteIngredientToRecipe(recipeId, ingredientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	}
