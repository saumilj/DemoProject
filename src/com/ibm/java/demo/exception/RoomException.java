package com.ibm.java.demo.exception;

public class RoomException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public RoomException() {
		
	}
	
	public RoomException(String message){
		
		super(message);
		
	}

	public RoomException(String message, Throwable cause){
		
		super(message, cause);
	
	}

}
