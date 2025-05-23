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

import com.healthybites.api.ApiError;
import com.healthybites.api.ApiResponseDto;
import com.healthybites.dtos.advice.AdviceResponseDto;
import com.healthybites.dtos.ingredient.IngredientResponseDto;
import com.healthybites.dtos.recipe.AddIngredientToRecipeDto;
import com.healthybites.dtos.recipe.RecipeRequestDto;
import com.healthybites.dtos.recipe.RecipeResponseDto;
import com.healthybites.dtos.user.UserEntityResponseDto;
import com.healthybites.service.recipe.RecipeServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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

    @Operation(
		    summary = "Get all recipes by user id from the database",
		    description = "Fetch all recipes available in the database by user id",
		    tags = {"Recipes"}
		)
		@ApiResponses(
		    value = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Advices fetched successfully",
		            content = {
		                @Content(
		                    mediaType = MediaType.APPLICATION_JSON_VALUE,
		                    schema = @Schema(
		                        implementation = ApiResponseDto.class,
		                        subTypes = {
		                        		AdviceResponseDto.class
		                        }
		                    )
		                )
		            }
		        ),
				@ApiResponse(responseCode = "500", 
				description = "Internal server error", 
				content = {
						@Content(
								schema = @Schema(
										implementation = ApiError.class, 
										example = """
												{
												    "status": "INTERNAL_SERVER_ERROR",
												    "message": "Could not open JPA EntityManager for transaction",
												    "errors": [
												        "An unexpected error occurred"
												    ],
												    "timestamp": "04/02/25 01:27:46"
												}
												 """)) })
		})
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
    
    @Operation(
		    summary = "Get a recipe by its ID from the database",
		    description = "Fetch a recipe based on the provided ID",
		    tags = {"Recipes"}
		)
		@ApiResponses(
		    value = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Recipe fetched successfully",
		            content = {
		                @Content(
		                    mediaType = MediaType.APPLICATION_JSON_VALUE,
		                    schema = @Schema(
		                        implementation = ApiResponseDto.class,
		                        subTypes = {
		                            UserEntityResponseDto.class
		                        }
		                    )
		                )
		            }
		        ),
		        @ApiResponse(responseCode = "404", 
				description = "User not found", 
				content = 
				@Content(
						schema = @Schema(
										implementation = ApiError.class, 
										example = """
												{
													"status": "NOT_FOUND",
													"message": "No endpoint GET /api/v1/recipes.",
													"errors": [
														"No handler found for GET /api/v1/recipes."
													],
													"timestamp": "04/02/25 01:15:06"
												}
												"""))),
				@ApiResponse(responseCode = "500", 
				description = "Internal server error", 
				content = {
						@Content(
								schema = @Schema(
										implementation = ApiError.class, 
										example = """
												{
												    "status": "INTERNAL_SERVER_ERROR",
												    "message": "Could not open JPA EntityManager for transaction",
												    "errors": [
												        "An unexpected error occurred"
												    ],
												    "timestamp": "04/02/25 01:27:46"
												}
												 """)) })
		})
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

    @Operation(
		    summary = "Create a new recipe",
		    description = "Add a new recipe to the database",
		    tags = {"Recipes"}
		)
		@ApiResponses(
		    value = {
		        @ApiResponse(
		            responseCode = "201",
		            description = "Recipe added successfully",
		            content = {
		                @Content(
		                    mediaType = MediaType.APPLICATION_JSON_VALUE,
		                    schema = @Schema(
		                        implementation = ApiResponseDto.class,
		                        subTypes = {
		                        		AdviceResponseDto.class
		                        }
		                    )
		                )
		            }
		        ),
		        @ApiResponse(responseCode = "500", 
				description = "Internal server error", 
				content = {
						@Content(
								schema = @Schema(
										implementation = ApiError.class, 
										example = """
												{
												    "status": "INTERNAL_SERVER_ERROR",
												    "message": "Could not open JPA EntityManager for transaction",
												    "errors": [
												        "An unexpected error occurred"
												    ],
												    "timestamp": "04/02/25 01:27:46"
												}
												 """)) })
		})	
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

    @Operation(
		    summary = "Get Ingredients For Recipe Id",
		    description = "Fetch a ingredient based on the provided ID",
		    tags = {"Recipes"}
		)
		@ApiResponses(
		    value = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Recipe fetched successfully",
		            content = {
		                @Content(
		                    mediaType = MediaType.APPLICATION_JSON_VALUE,
		                    schema = @Schema(
		                        implementation = ApiResponseDto.class,
		                        subTypes = {
		                            UserEntityResponseDto.class
		                        }
		                    )
		                )
		            }
		        ),
		        @ApiResponse(responseCode = "404", 
				description = "User not found", 
				content = 
				@Content(
						schema = @Schema(
										implementation = ApiError.class, 
										example = """
												{
													"status": "NOT_FOUND",
													"message": "No endpoint GET /api/v1/recipes.",
													"errors": [
														"No handler found for GET /api/v1/recipes."
													],
													"timestamp": "04/02/25 01:15:06"
												}
												"""))),
				@ApiResponse(responseCode = "500", 
				description = "Internal server error", 
				content = {
						@Content(
								schema = @Schema(
										implementation = ApiError.class, 
										example = """
												{
												    "status": "INTERNAL_SERVER_ERROR",
												    "message": "Could not open JPA EntityManager for transaction",
												    "errors": [
												        "An unexpected error occurred"
												    ],
												    "timestamp": "04/02/25 01:27:46"
												}
												 """)) })
		})
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


    @Operation(
		    summary = "Update an existing recipe",
		    description = "Update the recipe details based on the provided ID",
		    tags = {"Recipes"}
		)
		@ApiResponses(
		    value = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Recipe updated successfully",
		            content = {
		                @Content(
		                    mediaType = MediaType.APPLICATION_JSON_VALUE,
		                    schema = @Schema(
		                        implementation = ApiResponseDto.class,
		                        subTypes = {
		                            UserEntityResponseDto.class
		                        }
		                    )
		                )
		            }
		        ),
		        @ApiResponse(
			            responseCode = "400",
			            description = "Invalid input provided",
			            content = @Content(
			                schema = @Schema(
			                    implementation = ApiError.class,
			                    example = """
			                        {
									    "type": "about:blank",
									    "title": "Bad Request",
									    "status": 400,
									    "detail": "Failed to convert 'id' with value: 'a'",
									    "instance": "/api/v1/banks/a"
									}
			                    """
			                )
			            )
			        ),
			        @ApiResponse(
			            responseCode = "404",
			            description = "Advice not found",
			            content = @Content(
			                schema = @Schema(
			                    implementation = ApiError.class,
			                    example = """
			                        {
									    "status": "NOT_FOUND",
									    "message": "Advice with id 100 not found",
									    "errors": [
									        "Resource Not Found"
									    ],
									    "timestamp": "04/02/25 10:48:32"
									}
			                    """
			                )
			            )
			        ),
		        @ApiResponse(responseCode = "500", 
				description = "Internal server error", 
				content = {
						@Content(
								schema = @Schema(
										implementation = ApiError.class, 
										example = """
												{
												    "status": "INTERNAL_SERVER_ERROR",
												    "message": "Could not open JPA EntityManager for transaction",
												    "errors": [
												        "An unexpected error occurred"
												    ],
												    "timestamp": "04/02/25 01:27:46"
												}
												 """)) })
		})
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

    @Operation(
		    summary = "Delete a recipe by ID",
		    description = "Recipe a bank from the database based on the provided ID",
		    tags = {"Recipes"}
		)
		@ApiResponses(
		    value = {
		        @ApiResponse(
			            responseCode = "204",
			            description = "Recipe deleted successfully",
			            content = {@Content(schema = @Schema())}
			        ),
			        @ApiResponse(responseCode = "400", 
					description = "Bad request", 
					content =
							@Content(
									schema = @Schema(
											implementation = ApiError.class, 
											example = """
												 {
												    "type": "about:blank",
												    "title": "Bad Request",
												    "status": 400,
												    "detail": "Failed to convert 'id' with value: 'a'",
												    "instance": "/api/v1/advices"
													}
													                    """))),
			        @ApiResponse(
			            responseCode = "404",
			            description = "Advice not found",
			            content = @Content(
			                schema = @Schema(
			                    implementation = ApiError.class,
			                    example = """
			                        {
									    "status": "NOT_FOUND",
									    "message": "User with id 100 not found",
									    "errors": [
									        "Resource Not Found"
									    ],
									    "timestamp": "04/02/25 10:49:13"
									}
			                    """
			                )
			            )
			        ),
		        @ApiResponse(responseCode = "500", 
				description = "Internal server error", 
				content = {
						@Content(
								schema = @Schema(
										implementation = ApiError.class, 
										example = """
												{
												    "status": "INTERNAL_SERVER_ERROR",
												    "message": "Could not open JPA EntityManager for transaction",
												    "errors": [
												        "An unexpected error occurred"
												    ],
												    "timestamp": "04/02/25 01:27:46"
												}
												 """)) })
		})
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

    @Operation(
    	    summary = "Add an ingredient to a recipe",
    	    description = "Add a specific ingredient with quantity to a recipe based on the provided recipe ID",
    	    tags = {"Recipes"}
    	)
    	@ApiResponses(
    	    value = {
    	        @ApiResponse(
    	            responseCode = "200",
    	            description = "Ingredient added to recipe successfully",
    	            content = @Content(
    	                mediaType = MediaType.APPLICATION_JSON_VALUE,
    	                schema = @Schema(
    	                    implementation = ApiResponseDto.class
    	                )
    	            )
    	        ),
    	        @ApiResponse(
    	            responseCode = "400",
    	            description = "Invalid input provided",
    	            content = @Content(
    	                schema = @Schema(
    	                    implementation = ApiError.class,
    	                    example = """
    	                    {
    	                        "status": "BAD_REQUEST",
    	                        "message": "Invalid ingredient or quantity provided",
    	                        "errors": [
    	                            "Quantity must be greater than 0"
    	                        ],
    	                        "timestamp": "04/02/25 11:12:34"
    	                    }
    	                    """
    	                )
    	            )
    	        ),
    	        @ApiResponse(
    	            responseCode = "404",
    	            description = "Recipe or ingredient not found",
    	            content = @Content(
    	                schema = @Schema(
    	                    implementation = ApiError.class,
    	                    example = """
    	                    {
    	                        "status": "NOT_FOUND",
    	                        "message": "Ingredient or recipe not found",
    	                        "errors": [
    	                            "Resource Not Found"
    	                        ],
    	                        "timestamp": "04/02/25 11:15:22"
    	                    }
    	                    """
    	                )
    	            )
    	        ),
    	        @ApiResponse(
    	            responseCode = "500",
    	            description = "Internal server error",
    	            content = @Content(
    	                schema = @Schema(
    	                    implementation = ApiError.class,
    	                    example = """
    	                    {
    	                        "status": "INTERNAL_SERVER_ERROR",
    	                        "message": "Could not open JPA EntityManager for transaction",
    	                        "errors": [
    	                            "An unexpected error occurred"
    	                        ],
    	                        "timestamp": "04/02/25 01:27:46"
    	                    }
    	                    """
    	                )
    	            )
    	        )
    	    }
    	)

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
