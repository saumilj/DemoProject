package com.ibm.java.demo.service;

import org.json.JSONObject;

import com.ibm.java.demo.db.DatabaseQuery;
import com.ibm.java.demo.exception.CustomException;
import com.ibm.java.demo.exception.InvalidResponseException;

public class ReportManager {

	private DatabaseQuery dbq;
	
	public ReportManager(DatabaseQuery dbq){
		 
		 this.dbq = dbq;		
	}
	
	/*
	 * Call function to execute query to get all room-chair associations and return as a JSONObject to apis
	 */
	public JSONObject generateReport() throws InvalidResponseException {
		
		try{
			JSONObject response = dbq.getReport();
			if(!response.has("status")){
				throw new InvalidResponseException("Illegal response from server while retreiving report");
			}
			return response;		
		}
		catch (CustomException e){
			JSONObject errResponse = new JSONObject();
			errResponse.put("response", e.getMessage());
			errResponse.put("status", 500);
			return errResponse;	
		}
	}
}
