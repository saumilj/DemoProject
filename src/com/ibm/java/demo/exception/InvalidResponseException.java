package com.ibm.java.demo.exception;

public class InvalidResponseException extends Exception {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
	 * 
	 */
	
public InvalidResponseException(){
		
	}
	
	public InvalidResponseException(String message){
		
		super(message);
		
	}

	public InvalidResponseException(String message, Throwable cause){
		
		super(message, cause);
	
	}

}
