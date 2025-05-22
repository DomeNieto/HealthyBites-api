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

    /**
     * This Java function retrieves all ingredients and returns them in a JSON response.
     * 
     * @return The method `getAllIngredients()` returns a ResponseEntity object containing an
     * ApiResponseDto with a list of IngredientResponseDto objects. This response entity is returned
     * with an HTTP status code of 200 (OK).
     */
    @GetMapping(value = INGREDIENT_RESOURCE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto<List<IngredientResponseDto>>> getAllIngredients() {
        List<IngredientResponseDto> ingredients = ingredientService.getAllIngredients();
        ApiResponseDto<List<IngredientResponseDto>> response =
                new ApiResponseDto<>("Ingredients fetched successfully", HttpStatus.OK.value(), ingredients);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    /**
     * This function retrieves all active ingredients and returns them in a JSON response.
     * 
     * @return The method `getAllIngredientsActive` returns a `ResponseEntity` containing an
     * `ApiResponseDto` with a list of `IngredientResponseDto` objects representing all active
     * ingredients fetched from the `ingredientService`. The response message is "Ingredients fetched
     * successfully" with an HTTP status code of 200 (OK).
     */
    @GetMapping(value = INGREDIENT_RESOURCE + "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto<List<IngredientResponseDto>>> getAllIngredientsActive() {
        List<IngredientResponseDto> ingredients = ingredientService.getAllActiveIngredients();
        ApiResponseDto<List<IngredientResponseDto>> response =
                new ApiResponseDto<>("Ingredients fetched successfully", HttpStatus.OK.value(), ingredients);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    /**
     * This Java function creates a new ingredient by processing a POST request with JSON data and
     * returns a response with the created ingredient details.
     * 
     * @param dto The `dto` parameter in the `createIngredient` method is of type
     * `IngredientRequestDto`. It is annotated with `@Valid` to indicate that the input should be
     * validated based on the validation constraints defined in the `IngredientRequestDto` class. This
     * parameter represents the request body of the POST
     * @return The method is returning a ResponseEntity object containing an ApiResponseDto with a
     * success message, HTTP status code 201 (CREATED), and the created IngredientResponseDto object.
     */
    @PostMapping(value = INGREDIENT_RESOURCE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto<IngredientResponseDto>> createIngredient(@Valid @RequestBody IngredientRequestDto dto) {
        IngredientResponseDto created = ingredientService.createIngredient(dto);
        ApiResponseDto<IngredientResponseDto> response =
                new ApiResponseDto<>("Ingredient created successfully", HttpStatus.CREATED.value(), created);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

   /**
    * This Java function updates an ingredient using a PUT request and returns a response with the
    * updated ingredient details.
    * 
    * @param ingredientId The `ingredientId` is a path variable representing the unique identifier of
    * the ingredient that needs to be updated. It is extracted from the URL path of the request.
    * @param dto The "dto" parameter in the code snippet represents the request body containing data
    * for updating an ingredient. It is annotated with "@Valid" to indicate that the data in the
    * request body should be validated based on the validation constraints defined in the corresponding
    * DTO class (IngredientRequestDto). This parameter is used to
    * @return The method `updateIngredient` is returning a `ResponseEntity` containing an
    * `ApiResponseDto` with a message indicating that the ingredient was updated successfully, an HTTP
    * status code of 200 (OK), and the updated `IngredientResponseDto`.
    */
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
    
    /**
     * This Java function retrieves an ingredient by its ID and returns it in a JSON response.
     * @param ingredientId The `ingredientId` is a path variable representing the unique identifier of the ingredient
     * that you want to retrieve. It is extracted from the URL path of the request.
     * @return  The method `getIngredientById` returns a `ResponseEntity` containing an `ApiResponseDto` with a message indicating
     * that the ingredient was fetched successfully, an HTTP status code of 200 (OK), and the `IngredientResponseDto` object
     */
    @PutMapping(value = INGREDIENT_ID_PATH + "/reactivate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto<IngredientResponseDto>> reactivateIngredient(
            @PathVariable Long ingredientId
    ) {
    	IngredientResponseDto updated = ingredientService.reactivateIngredient(ingredientId);
        ApiResponseDto<IngredientResponseDto> response =
                new ApiResponseDto<>("Ingredient updated successfully", HttpStatus.OK.value(), updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This Java function disables an ingredient by its ID and returns a 204 No Content response.
     * 
     * @param ingredientId The `ingredientId` is a path variable representing the unique identifier of
     * the ingredient that you want to disable. It is extracted from the URL path of the request.
     * @return The method `disableIngredient` returns a `ResponseEntity` with a status of `NO_CONTENT`
     * (204) indicating that the request was successful, but there is no content to return in the
     * response body.
     */
    @PutMapping(value = INGREDIENT_ID_PATH + "/disable", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> disableIngredient(@PathVariable Long ingredientId) {
        ingredientService.disableIngredient(ingredientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
