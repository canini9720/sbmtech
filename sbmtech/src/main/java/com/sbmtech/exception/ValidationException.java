package com.sbmtech.exception;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 3231697657545358770L;
	
	public ValidationException(String message){
		super(message);
	}
	
}
