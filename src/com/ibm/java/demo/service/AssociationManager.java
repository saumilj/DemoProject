package com.ibm.java.demo.service;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ibm.java.demo.db.DatabaseQuery;
import com.ibm.java.demo.exception.CustomException;
import com.ibm.java.demo.exception.InvalidDataException;
import com.ibm.java.demo.exception.InvalidResponseException;

public class AssociationManager {

	DatabaseQuery dbq = new DatabaseQuery();
		/*
	 * Call function to execute queries to create association between chair and
	 * room
	 */
	public JSONObject associate(String names) throws InvalidResponseException {
		
		try {
			JSONObject jobj = new JSONObject(names);
			JSONObject response = dbq.associateChairToRoom(jobj.getString("Room"), jobj.getString("Chair"));
			if(!response.has("status")){
				throw new InvalidResponseException("Illegal response from server");
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
	 * Call function to execute queries to associate chair to a new room
	 */
	public JSONObject reassociate(String room, String chair) throws InvalidResponseException {

			
			try {
			JSONObject responseStr = dbq.deleteAssociation(chair);
			/*
			 * Once a Chair is de-associated with a room, call the function to associate it with the requested room
			 */
			if (responseStr.getInt("status") == 200) {
				JSONObject response = dbq.associateChairToRoom(room, chair);
				if(!response.has("response") || !response.has("status")){
					throw new InvalidResponseException("Illegal response from server");
				}
				return response;
			} else {
				JSONObject errResponse = new JSONObject();
				errResponse.put("response", "Error while deleting association");
				errResponse.put("status", 500);
				return errResponse;
			}
		} catch (CustomException e) {
			JSONObject errResponse = new JSONObject();
			errResponse.put("response", e.getMessage());
			errResponse.put("status", 500);
			return errResponse;
		}
	}

	/*
	 * Changes arrangement based on drag-drop actions
	 */
	public JSONObject changeAssociation(String json) throws InvalidDataException, InvalidResponseException {
		
		JSONObject jobj = new JSONObject(json);
		try {
			if (jobj.has("pool")) {

				try {
					JSONArray jchairs = (JSONArray) jobj.get("pool");
					for (int i = 0; i < jchairs.length(); i++) {
						dbq.deleteAssociation(jchairs.getString(i));
					}
				} catch (CustomException e) {
					JSONObject errResponse = new JSONObject();
					errResponse.put("response", e.getMessage());
					errResponse.put("status", 500);
					return errResponse;
				}
				jobj.remove("pool");
				
				if(jobj.keySet()==null){
					JSONObject response = new JSONObject();
					response.put("response", "All associations removed");
					response.put("status",200);
					return response;
				}
			}
			
			JSONObject response = dbq.changeArrangement(jobj.toString());
			if(!response.has("response") || !response.has("status")){
				throw new InvalidResponseException("Illegal response from server while changing arrangement");
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
