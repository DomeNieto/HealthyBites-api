package com.healthybites.api;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A generic wrapper for successful API responses.
 * Includes a timestamp, a message, an HTTP-style status code, and the response data.
 *
 * @param <T> The type of the data returned in the response
 */
@Getter
@Setter
@NoArgsConstructor
public class ApiResponseDto<T> {
	
	private LocalDateTime timestamp;
	private String message;
	private int code;
	private T data;
	
	public ApiResponseDto(String message, int code, T data ) {
		this.timestamp = LocalDateTime.now();
		this.message = message;
		this.code = code;
		this.data = data;
	}
}
