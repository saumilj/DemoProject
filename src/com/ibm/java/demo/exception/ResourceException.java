package com.ibm.java.demo.exception;

public class ResourceException extends Exception {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public ResourceException(){
		
	}
	
	public ResourceException(String message){
		
		super(message);		
	}

	public ResourceException(String message, Throwable cause){
		
		super(message, cause);
	
	}	
}
