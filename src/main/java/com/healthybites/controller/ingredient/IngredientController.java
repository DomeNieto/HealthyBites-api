package com.healthybites.controller.ingredient;

import com.healthybites.api.ApiResponseDto;
import com.healthybites.dtos.ingredient.IngredientRequestDto;
import com.healthybites.dtos.ingredient.IngredientResponseDto;
import com.healthybites.service.ingredient.IngredientServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class IngredientController {

    private static final String INGREDIENT_RESOURCE = "/ingredients";
    private static final String INGREDIENT_ID_PATH = INGREDIENT_RESOURCE + "/{ingredientId}";

    @Autowired
    private IngredientServiceImpl ingredientService;

    @GetMapping(value = INGREDIENT_RESOURCE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto<List<IngredientResponseDto>>> getAllIngredients() {
        List<IngredientResponseDto> ingredients = ingredientService.getAllIngredients();
        ApiResponseDto<List<IngredientResponseDto>> response =
                new ApiResponseDto<>("Ingredients fetched successfully", HttpStatus.OK.value(), ingredients);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping(value = INGREDIENT_ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto<IngredientResponseDto>> getIngredientById(@PathVariable Long ingredientId) {
    	IngredientResponseDto ingredient = ingredientService.getIngredientById(ingredientId);
        ApiResponseDto<IngredientResponseDto> response =
                new ApiResponseDto<>("Ingredient fetched successfully", HttpStatus.OK.value(), ingredient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = INGREDIENT_RESOURCE + "/recipe/{recipeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto<List<IngredientResponseDto>>> getIngredientsByRecipe(@PathVariable Long recipeId) {
        List<IngredientResponseDto> ingredients = ingredientService.getAllgetAllIngredientsByRecipe(recipeId);
        ApiResponseDto<List<IngredientResponseDto>> response =
                new ApiResponseDto<>("Ingredients by recipe fetched successfully", HttpStatus.OK.value(), ingredients);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = INGREDIENT_RESOURCE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto<IngredientResponseDto>> createIngredient(@Valid @RequestBody IngredientRequestDto dto) {
        IngredientResponseDto created = ingredientService.createIngredient(dto);
        ApiResponseDto<IngredientResponseDto> response =
                new ApiResponseDto<>("Ingredient created successfully", HttpStatus.CREATED.value(), created);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = INGREDIENT_ID_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto<IngredientResponseDto>> updateIngredient(
            @PathVariable Long ingredientId,
            @Valid @RequestBody IngredientRequestDto dto
    ) {
        IngredientResponseDto updated = ingredientService.updateIngredient(ingredientId, dto);
        ApiResponseDto<IngredientResponseDto> response =
                new ApiResponseDto<>("Ingredient updated successfully", HttpStatus.OK.value(), updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = INGREDIENT_ID_PATH)
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long ingredientId) {
        ingredientService.deleteIngredient(ingredientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
