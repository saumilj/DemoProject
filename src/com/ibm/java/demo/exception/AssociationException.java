package com.ibm.java.demo.exception;

public class AssociationException extends Exception {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AssociationException() {
		
	}
	
	public AssociationException(String message){
		
		super(message);
		
	}

	public AssociationException(String message, Throwable cause){
		
		super(message, cause);
	
	}

}
