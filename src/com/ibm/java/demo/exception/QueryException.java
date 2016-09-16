package com.ibm.java.demo.exception;

public class QueryException extends Exception{
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public QueryException() {
		
	}
	
	public QueryException(String message){
		
		super(message);
		
	}

	public QueryException(String message, Throwable cause){
		
		super(message, cause);
	
	}

}
