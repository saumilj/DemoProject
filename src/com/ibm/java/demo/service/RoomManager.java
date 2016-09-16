package com.ibm.java.demo.service;

import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.ibm.java.demo.db.DatabaseQuery;
import com.ibm.java.demo.entity.Room;
import com.ibm.java.demo.exception.RoomException;
import com.ibm.java.demo.exception.CustomException;
import com.ibm.java.demo.exception.DataValidationCheck;
import com.ibm.java.demo.exception.InvalidDataException;
import com.ibm.java.demo.exception.InvalidResponseException;
import com.ibm.java.demo.exception.RoomException;

public class RoomManager {
	
DataValidationCheck validate = new DataValidationCheck();
	
	private DatabaseQuery dbq;

	public RoomManager(DatabaseQuery dbq){
	 
		this.dbq = dbq;		
	}
	/*
	 * Call function to execute query to create new Room
	 */
	
	public JSONObject createRoom(String roomName){
		
		try{
			JSONObject jobj = new JSONObject(roomName);
			Room room = new Room(jobj.getString("Name"));
			room = dbq.createRoom(room);
			
			JSONObject response = new JSONObject();
			response.put("response","Room inserted successfully successfully.");
			response.put("status", 200);
			response.put("Id", room.getRoomId());		
			
			return response;
			
		}catch (CustomException | RoomException e){
			
			//formulate a response object
			JSONObject errResponse = new JSONObject();
			errResponse.put("response", e.getMessage());
			errResponse.put("status", 500);
			System.out.print(errResponse.get("response").toString());
			
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
	
	public JSONObject deleteRoom(String roomName) throws InvalidDataException, InvalidResponseException{
		
		try{
			//Delete association of the room with room			
			JSONObject response = dbq.deleteAssociation(roomName);
			if (response.getInt("status")==200){
			//Once association is deleted, then safely remove it from Rooms table
			JSONObject responseStr = dbq.deleteRoom(roomName);
			if(!response.has("response") || !responseStr.has("status")){
				throw new InvalidResponseException("Illegal response from server while deleting room");
			}
			return responseStr;
			}
			else{
				JSONObject errResponse = new JSONObject();
				errResponse.put("response", "Could not delete association of room");
				errResponse.put("status", 500);
				return errResponse;		
			}
		}
		catch(CustomException e){
			JSONObject errResponse = new JSONObject();
			errResponse.put("response", e.getMessage());
			errResponse.put("status", 500);
			return errResponse;	
		}
	}
}
