package com.ibm.java.test;

import static org.junit.Assert.fail;

import org.json.JSONObject;
import org.junit.Test;

import com.ibm.java.demo.exception.InvalidDataException;
import com.ibm.java.demo.exception.InvalidResponseException;
import com.ibm.java.demo.service.RoomManager;

public class RoomTests {
	
	// Negative Test create Room Method
	@Test
	public void testNullParam() throws InvalidResponseException{
		
		RoomManager cm = new RoomManager();
		try{
			cm.createRoom(null);
			fail("should throw exception");
		}
		catch(InvalidDataException e){
			// should catch this exception
		}	
	}
	
	@Test
	public void testEmptyParam() throws InvalidResponseException{
		
		RoomManager cm = new RoomManager();
		try{
			cm.createRoom("");
			fail("should throw exception");
		}
		catch(InvalidDataException e){
			// should catch this exception
		}	
	}
	
	@Test
	public void missingNameKey() throws InvalidResponseException{
		
		RoomManager cm = new RoomManager();
		try{
			JSONObject j = new JSONObject();
			j.put("test", "data");
			cm.createRoom(j.toString());
			fail("should throw exception");
		}
		catch(InvalidDataException e){
			// should catch this exception
		}	
	}
}
