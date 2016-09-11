package com.ibm.java.demo.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.ibm.java.demo.db.DatabaseQuery;

public class Chair {
	
	DatabaseQuery dbq = new DatabaseQuery();

	public Response createChair(String chairName){
		
		JSONObject jobj = new JSONObject(chairName);				
		try{
			String responseStr = dbq.createResource(jobj.getString("Name"), "Chair");
			JSONObject jres = new JSONObject(responseStr);
			return Response.status(jres.getInt("code")).entity(jres.toString()).build();
		}
		// Convert Error message as variable
		catch (IOException e) {
			e.printStackTrace();
			return Response.status(500).entity("Error occurred during application startup. Please contact system administrator").build();
		}
		catch (SQLException e) {
			e.printStackTrace();
			return Response.status(500).entity("Error occurred during database operation while trying to create a chair. Please contact").build();
		}
		catch (NamingException e) {
			return Response.status(500).entity("Error occurred during database operation while trying to create a chair. Please contact system administrator").build();
		}
		catch (Exception e){
			return Response.status(500).entity("Error occurred. Please contact system administrator").build();
		}
	}
	
	public Response getChairNames(){
		
		try {
			String responseStr = dbq.getNames("Chair");
			return Response.status(200).entity(responseStr).build();
		} 
		catch (IOException e) {
			e.printStackTrace();
			return Response.status(500).entity("Error occurred during application startup. Please contact system administrator").build();
		}
		catch (SQLException e) {
			e.printStackTrace();
			return Response.status(500).entity("Error occurred during database operation while trying to create a chair. Please contact").build();
		}
		catch (NamingException e) {
			return Response.status(500).entity("Error occurred during database operation while trying to create a chair. Please contact system administrator").build();
		}
		catch (Exception e){
			return Response.status(500).entity("Error occurred. Please contact system administrator").build();
		}
	}

}
