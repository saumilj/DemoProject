package com.ibm.java.test;

import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.Test;

import com.ibm.java.demo.exception.InvalidDataException;
import com.ibm.java.demo.exception.InvalidResponseException;
import com.ibm.java.demo.service.ChairManager;

public class ChairTests {
	
	//Negative tests for create chair method
	@Test
	public void testNullParam() throws InvalidResponseException{
		
		ChairManager cm = new ChairManager();
		try{
			cm.createChair(null);
			fail("should throw exception");
		}
		catch(InvalidDataException e){
			// should catch this exception
		}	
	}
	
	@Test
	public void testEmptyParam() throws InvalidResponseException{
		
		ChairManager cm = new ChairManager();
		try{
			cm.createChair("");
			fail("should throw exception");
		}
		catch(InvalidDataException e){
			// should catch this exception
		}	
	}
	
	@Test
	public void missingNameKey() throws InvalidResponseException{
		
		ChairManager cm = new ChairManager();
		try{
			JSONObject j = new JSONObject();
			j.put("test", "data");
			cm.createChair(j.toString());
			fail("should throw exception");
		}
		catch(InvalidDataException e){
			// should catch this exception
		}	
	}
}
