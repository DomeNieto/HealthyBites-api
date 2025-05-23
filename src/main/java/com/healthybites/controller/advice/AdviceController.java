package com.healthybites.controller.advice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.healthybites.dtos.advice.AdviceRequestDto;
import com.healthybites.dtos.advice.AdviceResponseDto;
import com.healthybites.dtos.user.UserEntityResponseDto;
import com.healthybites.service.advice.AdviceServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class AdviceController {

    private static final String ADVICE_RESOURCE = "/advices";
    private static final String ADVICE_ID_PATH = ADVICE_RESOURCE + "/{adviceId}";

    @Autowired
    private AdviceServiceImpl adviceService;

    @Operation(
		    summary = "Get all advices from the database",
		    description = "Fetch all advices available in the database",
		    tags = {"Advice"}
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
     * * Get all advices
     * @return The method `getAllAdvices()` returns a `ResponseEntity` containing an
     * `ApiResponseDto` with a list of `AdviceResponseDto` objects representing all advices
     * fetched from the service. The response message is "Advices fetched successfully" with an
     * HTTP status code of 200 (OK).
     */
    @GetMapping(value = ADVICE_RESOURCE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto<List<AdviceResponseDto>>> getAllAdvices() {
        List<AdviceResponseDto> advices = adviceService.getAllAdvices();
        ApiResponseDto<List<AdviceResponseDto>> response =
                new ApiResponseDto<>("Advices fetched successfully", HttpStatus.OK.value(), advices);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
		    summary = "Create a new advice",
		    description = "Add a new advice to the database",
		    tags = {"Advice"}
		)
		@ApiResponses(
		    value = {
		        @ApiResponse(
		            responseCode = "201",
		            description = "Advice added successfully",
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
     * Create advice
     * @param adviceRequestDto the advice request dto to be created
     * @return The method `createAdvice` returns a `ResponseEntity` containing an
     * `ApiResponseDto` with an `AdviceResponseDto` object representing the created advice. The
     * response message is "Advice created successfully" with an HTTP status code of 201 (Created).
     * The response entity is created with the `HttpStatus.CREATED` status.
     */
    @PostMapping(value = ADVICE_RESOURCE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto<AdviceResponseDto>> createAdvice(
            @Valid @RequestBody AdviceRequestDto adviceRequestDto) {
        AdviceResponseDto createdAdvice = adviceService.createAdvice(adviceRequestDto);
        ApiResponseDto<AdviceResponseDto> response =
                new ApiResponseDto<>("Advice created successfully", HttpStatus.CREATED.value(), createdAdvice);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
		    summary = "Update an existing advice",
		    description = "Update the advice details based on the provided ID",
		    tags = {"Advice"}
		)
		@ApiResponses(
		    value = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Ingredient updated successfully",
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
     * Update advice by id
     * @param adviceId id of the advice to be updated
     * @param adviceRequestDto the advice request dto
     * @return The method `updateAdvice` returns a `ResponseEntity` containing an
     * `ApiResponseDto` with an `AdviceResponseDto` object representing the updated advice. The 
     * response message is "Advice updated successfully" with an HTTP status code of 200 (OK).
     */
    @PutMapping(value = ADVICE_ID_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseDto<AdviceResponseDto>> updateAdvice(
            @PathVariable Long adviceId, @Valid @RequestBody AdviceRequestDto adviceRequestDto) {
        AdviceResponseDto updatedAdvice = adviceService.updateAdvice(adviceId, adviceRequestDto);
        ApiResponseDto<AdviceResponseDto> response =
                new ApiResponseDto<>("Advice updated successfully", HttpStatus.OK.value(), updatedAdvice);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
		    summary = "Delete a advice by ID",
		    description = "Advice a bank from the database based on the provided ID",
		    tags = {"Advice"}
		)
		@ApiResponses(
		    value = {
		        @ApiResponse(
			            responseCode = "204",
			            description = "Advice deleted successfully",
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
     * Delete advice by id
     * @param adviceId id of the advice to be deleted
     * @return The method `deleteAdvice` returns a `ResponseEntity` with an HTTP status code of 204 (No Content)
     * indicating that the advice has been successfully deleted.
     * The response does not contain any content.
     */
    @DeleteMapping(value = ADVICE_ID_PATH)
    public ResponseEntity<Void> deleteAdvice(@PathVariable Long adviceId) {
        adviceService.deleteAdvice(adviceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
