package com.ibm.java.demo.service;

import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.ibm.java.demo.db.DatabaseQuery;
import com.ibm.java.demo.exception.CustomException;
import com.ibm.java.demo.exception.DataValidationCheck;
import com.ibm.java.demo.exception.InvalidDataException;
import com.ibm.java.demo.exception.InvalidResponseException;

public class RoomManager {
	
DatabaseQuery dbq = new DatabaseQuery();
DataValidationCheck validate = new DataValidationCheck();
	
	/*
	 * Call function to execute query to create new Room
	 */
	public JSONObject createRoom(String roomName) throws InvalidDataException, InvalidResponseException {
		
		validate.checkEmpty(roomName);
		validate.checkKeys(roomName, "Name");
		try{
			// some more validation checks
			JSONObject jobj = new JSONObject(roomName);
			JSONObject response = dbq.createResource(jobj.getString("Name"), "Room");
			if(!response.has("response") || !response.has("status")){
				throw new InvalidResponseException("Illegal response from server while creating room");
			}
			return response;
		}catch (CustomException e){
			JSONObject errResponse = new JSONObject();
			errResponse.put("response", e.getMessage());
			errResponse.put("status", 500);
			return errResponse;
		}
	}
	
	/*
	 * Call function to execute queries to get all RoomIds from the database
	 */
	public JSONObject getRoomNames() throws InvalidResponseException{
		
		try {
			JSONObject response = dbq.getNames("Room");
			if(!response.has("status")){
				throw new InvalidResponseException("Illegal response from server while getting room names");
			}
			return response;
		} catch (CustomException e) {
			JSONObject errResponse = new JSONObject();
			errResponse.put("response", e.getMessage());
			errResponse.put("status", 500);
			return errResponse;
		}
		
	}
}
