package com.healthybites.service.user;

import java.util.List;

import com.healthybites.dtos.user.UserEntityRequestDto;
import com.healthybites.dtos.user.UserEntityResponseDto;

public interface UserService {
	
	UserEntityResponseDto createUser(UserEntityRequestDto userRequestDto);
	List<UserEntityResponseDto> getAllUsers();
	UserEntityResponseDto getUserById(Long id);
	UserEntityResponseDto updateUser(Long id, UserEntityRequestDto userRequestDto);
	void deleteUser(Long id);
	UserEntityResponseDto findUserByEmail(String email);
	
}
