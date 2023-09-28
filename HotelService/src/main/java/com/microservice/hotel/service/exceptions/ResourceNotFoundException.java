package com.microservice.hotel.service.exceptions;

public class ResourceNotFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4783668267168355792L;

	public ResourceNotFoundException(String s){
		super(s);
	}
	
	public ResourceNotFoundException() {
		super("Resource Not Found!");
	}

}
