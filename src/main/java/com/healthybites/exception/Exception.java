package com.healthybites.exception;

/**
 * Custom runtime exception class.
 * Extends RuntimeException to provide application-specific exceptions.
 */
public class Exception extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public Exception(String message) {
		super(message);		
	}
	
	public Exception(String message, Throwable cause) {
		super(message, cause);		
	}
}
