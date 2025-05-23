package com.healthybites.exception;

/**
 * Exception thrown when a requested resource is not found.
 * Extends RuntimeException to represent an unchecked exception.
 */
public class ResourceNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
		super(message);		
	}
	
	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);		
	}
}
