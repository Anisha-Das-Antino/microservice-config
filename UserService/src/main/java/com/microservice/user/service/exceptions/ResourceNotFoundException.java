package com.microservice.user.service.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3104182559545238843L;

	public ResourceNotFoundException() {
		super("Resource not found on server!");
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}

}
