package com.healthybites.dtos.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Data Transfer Object (DTO) representing the response returned after a successful authentication.
* Contains the access token and its type Bearer.
*/
@Data
@NoArgsConstructor
public class AuthResponseDto {

	private String accessToken;
	private String tokenType = "Bearer ";
	
	public AuthResponseDto(String accessToken) {
		this.accessToken = accessToken;
	}
}
