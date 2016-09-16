package com.ibm.java.demo.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.ibm.java.demo.db.DatabaseQuery;
import com.ibm.java.demo.entity.Chair;
import com.ibm.java.demo.exception.CustomException;
import com.ibm.java.demo.exception.DataValidationCheck;
import com.ibm.java.demo.exception.InvalidDataException;
import com.ibm.java.demo.exception.InvalidResponseException;

public class ChairManager {
	
	public ChairManager(){}
	
	DatabaseQuery dbq = new DatabaseQuery();
	DataValidationCheck validate = new DataValidationCheck();
	/*
	 * Call function to execute query to create new Chair
	 */
	public JSONObject createChair(String chairName) throws InvalidDataException, InvalidResponseException{
		
		validate.checkEmpty(chairName);
		validate.checkKeys(chairName, "Name");
		try{
			JSONObject jobj = new JSONObject(chairName);
			Chair chair = new Chair(jobj.getString("Name"));
			JSONObject response = dbq.createResource(chair.getChairName(), "Chair");
			if(!response.has("response") || !response.has("status")){
				throw new InvalidResponseException("Illegal response from server while creating chair");
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
				
		if(chairName.isEmpty() || chairName==null){
			throw new InvalidDataException("Room Data is not valid");
		}
		try{
			//Delete association of the chair with room			
			JSONObject response = dbq.deleteAssociation(chairName);
			if (response.getInt("status")==200){
			//Once association is deleted, then safely remove it from Chairs table
			JSONObject responseStr = dbq.deleteChair(chairName);
			if(!response.has("response") || !responseStr.has("status")){
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
