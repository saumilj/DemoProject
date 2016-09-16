package com.ibm.java.demo.service;

import org.json.JSONObject;
import com.ibm.java.demo.db.DatabaseQuery;
import com.ibm.java.demo.entity.Chair;
import com.ibm.java.demo.exception.ChairException;
import com.ibm.java.demo.exception.CustomException;
import com.ibm.java.demo.validation.DataValidationCheck;
import com.ibm.java.demo.exception.InvalidDataException;
import com.ibm.java.demo.exception.InvalidResponseException;

public class ChairManager {

	private DatabaseQuery dbq;
	
	public ChairManager(DatabaseQuery dbq){
		 
		 this.dbq = dbq;		
	}
	
	DataValidationCheck validate = new DataValidationCheck();	
	
	/*
	 * Call function to execute query to create new Chair
	 */
	public JSONObject createChair(String chairName){
		
		try{
			
			JSONObject jobj = new JSONObject(chairName);
			Chair chair = new Chair(jobj.getString("Name"));
			chair = dbq.createChair(chair);
			
			//formulate a response object
			JSONObject response = new JSONObject();
			response.put("status", 200);
			response.put("response","Chair inserted successfully.");
			response.put("Id", chair.getChairId());	
			
			return response;
			
		}catch (CustomException e){
			
			JSONObject errResponse = new JSONObject();
			errResponse.put("response", e.getMessage());
			errResponse.put("status", 500);
			return errResponse;
		}catch (ChairException e){
			
			JSONObject errResponse = new JSONObject();
			errResponse.put("response", e.getMessage());
			errResponse.put("status", 500);
			return errResponse;
		}
	}	
	/*
	 * Call function to execute queries to get all ChairIds from the database
	 */
	
	public JSONObject getChairNames() throws InvalidResponseException{
				
		try {
			JSONObject response = dbq.getNames("Chair");
			if(!response.has("status")){
				throw new InvalidResponseException("Illegal response from server while getting chairs");
			}
			return response;
		} catch (CustomException e) {
			JSONObject errResponse = new JSONObject();
			errResponse.put("response", e.getMessage());
			errResponse.put("status", 500);
			return errResponse;		
		}
	}
	/*
	 * Delete the chair safely from database
	 */
	public JSONObject deleteChair(String chairName) throws InvalidDataException, InvalidResponseException{
				
		try{
			//Delete association of the chair with room			
			JSONObject response = dbq.deleteAssociation(chairName);
			if (response.getInt("status")==200){
			//Once association is deleted, then safely remove it from Chairs table
				JSONObject responseStr = dbq.deleteChair(chairName);
				if(!responseStr.has("response") || !responseStr.has("status")){
					throw new InvalidResponseException("Illegal response from server while deleting chair");
				}
				return responseStr;
			}
			else{
				JSONObject errResponse = new JSONObject();
				errResponse.put("response", "Could not delete association of chair");
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
