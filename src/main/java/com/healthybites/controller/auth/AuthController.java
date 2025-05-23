package com.healthybites.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthybites.dtos.auth.AuthLoginRequestDto;
import com.healthybites.dtos.auth.AuthResponseDto;
import com.healthybites.service.userDetails.UserDetailsServiceImpl;

/**
 * Controller that handles authentication-related operations.
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins="*", allowedHeaders="*")
public class AuthController {
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	 
	/**
     * Authenticates a user with provided login credentials.
     *
     * @param loginRequest The login request containing username/email and password.
     * @return A response entity containing an authentication token and user details if successful.
     *
     * Example request body:
     * {
     *   "username": "user@example.com",
     *   "password": "securePassword123"
     * }
     */
		@PostMapping("/login")
		public ResponseEntity<AuthResponseDto> login(@RequestBody AuthLoginRequestDto loginRequest) {
			return new ResponseEntity<>(userDetailsService.login(loginRequest), HttpStatus.OK);
		}
}

