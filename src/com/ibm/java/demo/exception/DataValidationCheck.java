package com.ibm.java.demo.exception;

import org.json.JSONObject;

public class DataValidationCheck {
	
	public void checkEmpty(String names) throws InvalidDataException{
		
		if(names==null || names.isEmpty()){
			throw new InvalidDataException("Data entered is not valid");
		}		
	}
	
	public void checkKeys(String name, String key) throws InvalidDataException{
		
		JSONObject jobj = new JSONObject(name);
		if(!jobj.has(key) || jobj.getString(key).isEmpty()){
			throw new InvalidDataException(key+" Data missing");
		}
	}
}
