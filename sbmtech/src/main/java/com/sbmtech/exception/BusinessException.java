package com.sbmtech.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 346591360907372884L;
	
	
	public BusinessException(String message){
		
		super(message);
		
	}
	
}

