package com.ibm.java.demo.exception;

public class InvalidDataException extends Exception {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 4125112556780361223L;

public InvalidDataException(){
		
	}
	
	public InvalidDataException(String message){
		
		super(message);
		
	}

	public InvalidDataException(String message, Throwable cause){
		
		super(message, cause);
	
	}

}
