package com.healthybites.controller.user;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.healthybites.api.ApiError;
import com.healthybites.api.ApiResponseDto;
import com.healthybites.dtos.user.UserEntityRequestDto;
import com.healthybites.dtos.user.UserEntityResponseDto;
import com.healthybites.service.user.UserServiceImpl;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

// Controller user
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins="*", allowedHeaders="*")
@Tag(name="User", description="Controller for User")
public class UserController {
	
	@Autowired
	UserServiceImpl userService;
	
	private static final String USER_RESOURCE = "/users";
	private static final String CURRENT_USER_RESOURCE = USER_RESOURCE + "/by-email";
	private static final String USER_ID_PATH = USER_RESOURCE + "/{userId}";
	
	
	
	@Operation(
		    summary = "Create a new user",
		    description = "Add a new user to the database",
		    tags = {"User"}
		)
		@ApiResponses(
		    value = {
		        @ApiResponse(
		            responseCode = "201",
		            description = "User added successfully",
		            content = {
		                @Content(
		                    mediaType = MediaType.APPLICATION_JSON_VALUE,
		                    schema = @Schema(
		                        implementation = ApiResponseDto.class,
		                        subTypes = {
		                            UserEntityResponseDto.class
		                        },
		                        example = """
		                            {
									    "timestamp": "2025-02-04T23:01:59.5473058",
									    "message": "Bank added succesfully",
									    "code": 200,
									    "data": {
			                        		"id": 1,
										    "name": "Eduardo  Ortiz",
										    "registrationDate":"2025-02-04T22:52:40.2276103",,
										    "infoUser": {
										        "height": 1.35,
										        "weight": 80.0,
										        "age": 33,
										        "sex": "M",
										        "activityLevel": "Media"
										    }
									    }
									}
		                        """
		                    )
		                )
		            }
		        ),
		        @ApiResponse(
			            responseCode = "404",
			            description = "Bad request",
			            content = @Content(
			                schema = @Schema(
			                    implementation = ApiError.class,
			                    example = """
										{
										    "status": "NOT_FOUND",
										    "message": "No endpoint GET /api/v1/useras.",
										    "errors": [
										        "No handler found for GET /api/v1/useras"
										    ],
										    "timestamp": "04/02/25 11:02:42"
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
	@PostMapping(value = USER_RESOURCE,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<ApiResponseDto<UserEntityResponseDto>> createUser(@Valid @RequestBody UserEntityRequestDto
			userRequestDto){
		UserEntityResponseDto createdUser = userService.createUser(userRequestDto);

		ApiResponseDto<UserEntityResponseDto> response = new ApiResponseDto<>("User added succesfully", HttpStatus.CREATED.value(), createdUser);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	
	
	@Operation(
		    summary = "Get all users from the database",
		    description = "Fetch all users available in the database",
		    tags = {"User"}
		)
		@ApiResponses(
		    value = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Users fetched successfully",
		            content = {
		                @Content(
		                    mediaType = MediaType.APPLICATION_JSON_VALUE,
		                    schema = @Schema(
		                        implementation = ApiResponseDto.class,
		                        subTypes = {
		                            UserEntityResponseDto.class
		                        },
		                        example = """
		                           {
								    "timestamp": "2025-02-04T22:52:40.2276103",
								    "message": "Users fetched succesfully",
								    "code": 200,
								    "data": [
								       {
		                        		"id": 1,
									    "name": "Eduardo Ortiz",
									    "registrationDate":"2025-02-04T22:52:40.2276103",,
									    "infoUser": {
									        "height": 1.35,
									        "weight": 80.0,
									        "age": 33,
									        "sex": "M",
									        "activityLevel": "Media"
									    }
									}
									 {
								    "timestamp": "2025-02-04T22:52:40.2276103",
								    "message": "Users fetched succesfully",
								    "code": 200,
								    "data": [
								       {
		                        		"id": 2,
									    "name": "Amelia Ortiz",
									    "registrationDate":"2025-02-04T22:52:40.2276103",,
									    "infoUser": {
									        "height": 1.35,
									        "weight": 80.0,
									        "age": 33,
									        "sex": "F",
									        "activityLevel": "Media"
									    }
									}
		                        """
		                    )
		                )
		            }
		        ),
		        @ApiResponse(responseCode = "404", 
				description = "Users not found", 
				content = 
				@Content(
						schema = @Schema(
										implementation = ApiError.class, 
										example = """
												{
													"status": "NOT_FOUND",
													"message": "No endpoint GET /api/v1/usersa.",
													"errors": [
														"No handler found for GET /api/v1/usersa."
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
	@GetMapping(value = USER_RESOURCE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<List<UserEntityResponseDto>>> getAllUsers(){
		List<UserEntityResponseDto> users =  userService.getAllUsers();

		ApiResponseDto<List<UserEntityResponseDto>> response = new ApiResponseDto<>("Users fetched succesfully", HttpStatus.OK.value(), users);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@Operation(
		    summary = "Get a user by its ID from the database",
		    description = "Fetch a user based on the provided ID",
		    tags = {"User"}
		)
		@ApiResponses(
		    value = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "User fetched successfully",
		            content = {
		                @Content(
		                    mediaType = MediaType.APPLICATION_JSON_VALUE,
		                    schema = @Schema(
		                        implementation = ApiResponseDto.class,
		                        subTypes = {
		                            UserEntityResponseDto.class
		                        },
		                        example = """
		                           {
								    "timestamp": "2025-02-04T22:52:40.2276103",
								    "message": "Users fetched succesfully",
								    "code": 200,
								    "data": [
								       {
		                        		"id": 1,
									    "name": "Eduardo Ortiz",
									    "registrationDate":"2025-02-04T22:52:40.2276103",,
									    "infoUser": {
									        "height": 1.35,
									        "weight": 80.0,
									        "age": 33,
									        "sex": "M",
									        "activityLevel": "Media"
									    }
									}
		                        """
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
													"message": "No endpoint GET /api/v1/usesa.",
													"errors": [
														"No handler found for GET /api/v1/usesa."
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
	@GetMapping(value = USER_ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<UserEntityResponseDto>> getUserById(@PathVariable Long userId) {
		UserEntityResponseDto user  = userService.getUserById(userId);

		ApiResponseDto<UserEntityResponseDto> response = new ApiResponseDto<>("User fetched succesfully", HttpStatus.OK.value(), user);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	@Operation(
		    summary = "Get a user by its email from the database",
		    description = "Fetch a user based on the provided ID",
		    tags = {"User"}
		)
		@ApiResponses(
		    value = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "User fetched successfully",
		            content = {
		                @Content(
		                    mediaType = MediaType.APPLICATION_JSON_VALUE,
		                    schema = @Schema(
		                        implementation = ApiResponseDto.class,
		                        subTypes = {
		                            UserEntityResponseDto.class
		                        },
		                        example = """
		                           {
								    "timestamp": "2025-02-04T22:52:40.2276103",
								    "message": "User fetched succesfully",
								    "code": 200,
								    "data":
								       {
		                        		"id": 1,
									    "name": "Eduardo Ortiz",
									    "registrationDate":"2025-02-04T22:52:40.2276103",,
									    "infoUser": {
									        "height": 1.35,
									        "weight": 80.0,
									        "age": 33,
									        "sex": "M",
									        "activityLevel": "Media"
									    }
									}
		                        """
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
													"message": "No endpoint GET /api/v1/usesa.",
													"errors": [
														"No handler found for GET /api/v1/usesa."
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
	@GetMapping(value = CURRENT_USER_RESOURCE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<UserEntityResponseDto>> getUserByEmail( @RequestParam  String email) {
		UserEntityResponseDto user  = userService.findUserByEmail(email);

		ApiResponseDto<UserEntityResponseDto> response = new ApiResponseDto<>("User fetched succesfully", HttpStatus.OK.value(), user);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	
	@Operation(
		    summary = "Update an existing user",
		    description = "Update the user details based on the provided ID",
		    tags = {"User"}
		)
		@ApiResponses(
		    value = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "User updated successfully",
		            content = {
		                @Content(
		                    mediaType = MediaType.APPLICATION_JSON_VALUE,
		                    schema = @Schema(
		                        implementation = ApiResponseDto.class,
		                        subTypes = {
		                            UserEntityResponseDto.class
		                        },
		                        example = """
		                            {
									    "timestamp": "2025-02-04T23:04:05.4916138",
									    "message": "User update succesfully",
									    "code": 200,
									    "data": {
			                        		"id": 1,
										    "name": "Eduardo Alberto Ortiz",
										    "registrationDate":"2025-02-04T22:52:40.2276103",,
										    "infoUser": {
										        "height": 1.35,
										        "weight": 80.0,
										        "age": 33,
										        "sex": "M",
										        "activityLevel": "Media"
										    }
									    }
									}
		                        """
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
			            description = "User not found",
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
	@PutMapping(value = USER_ID_PATH,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<UserEntityResponseDto>> updateUser(@PathVariable Long userId,
			@Valid @RequestBody UserEntityRequestDto userRequestDto){

		UserEntityResponseDto updateUser = userService.updateUser(userId, userRequestDto);
		ApiResponseDto<UserEntityResponseDto> response = new ApiResponseDto<>("User update succesfully", HttpStatus.OK.value(),  updateUser);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	@Operation(
		    summary = "Delete a user by ID",
		    description = "User a bank from the database based on the provided ID",
		    tags = {"User"}
		)
		@ApiResponses(
		    value = {
		        @ApiResponse(
			            responseCode = "204",
			            description = "User deleted successfully",
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
												    "instance": "/api/v1/banks/a"
													}
													                    """))),
			        @ApiResponse(
			            responseCode = "404",
			            description = "User not found",
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
	@DeleteMapping(value = USER_ID_PATH)
	public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
		userService.deleteUser(userId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
