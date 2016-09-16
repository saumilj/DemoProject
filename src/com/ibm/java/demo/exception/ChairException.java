package com.ibm.java.demo.exception;

public class ChairException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public ChairException() {
		
	}
	
	public ChairException(String message){
		
		super(message);
		
	}

	public ChairException(String message, Throwable cause){
		
		super(message, cause);
	
	}
}
