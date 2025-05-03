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
import org.springframework.web.bind.annotation.RestController;

import com.healthybites.api.ApiResponseDto;
import com.healthybites.dtos.user.UserEntityRequestDto;
import com.healthybites.dtos.user.UserEntityResponseDto;
import com.healthybites.service.user.UserServiceImpl;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins="*", allowedHeaders="*")
public class UserController {
	
	@Autowired
	UserServiceImpl userService;
	
	private static final String USER_RESOURCE = "/users";
	private static final String USER_ID_PATH = USER_RESOURCE + "/{userId}";
	
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
	
	@GetMapping(value = USER_RESOURCE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<List<UserEntityResponseDto>>> getAllUsers(){
		List<UserEntityResponseDto> users =  userService.getAllUsers();

		ApiResponseDto<List<UserEntityResponseDto>> response = new ApiResponseDto<>("Users fetched succesfully", HttpStatus.OK.value(), users);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = USER_ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<UserEntityResponseDto>> getUserById(@PathVariable Long userId) {
		UserEntityResponseDto user  = userService.getUserById(userId);

		ApiResponseDto<UserEntityResponseDto> response = new ApiResponseDto<>("User fetched succesfully", HttpStatus.OK.value(), user);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	@PutMapping(value = USER_ID_PATH,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<UserEntityResponseDto>> updateUser(@PathVariable Long userId,
			@Valid @RequestBody UserEntityRequestDto userRequestDto){

		UserEntityResponseDto updateUser = userService.updateUser(userId, userRequestDto);
		ApiResponseDto<UserEntityResponseDto> response = new ApiResponseDto<>("User update succesfully", HttpStatus.OK.value(),  updateUser);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	@DeleteMapping(value = USER_ID_PATH)
	public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
		userService.deleteUser(userId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
