package com.ibm.java.demo.service;

import com.ibm.java.demo.db.DatabaseQuery;

import javax.naming.NamingException;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;

public class RoomChair {
	
	DatabaseQuery dbq = new DatabaseQuery();
	
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
	
	public Response getRoomNames(){
		
		try {
			String responseStr = dbq.getNames("Room");
			return Response.status(200).entity(responseStr).build();
		} 
		catch (IOException e) {
			e.printStackTrace();
			return Response.status(500).entity("Error occurred during application startup. Please contact system administrator").build();
		}
		catch (SQLException e) {
			e.printStackTrace();
			return Response.status(500).entity("Error occurred during database operation while retreiving room names. Please contact").build();
		}
		catch (NamingException e) {
			return Response.status(500).entity("Error occurred during database operation while retreiving room names. Please contact system administrator").build();
		}
		catch (Exception e){
			return Response.status(500).entity("Error occurred. Please contact system administrator").build();
		}
	}
	
	public Response associate(String names){
		
		JSONObject jobj = new JSONObject(names);
		try{
			String responseStr = dbq.associate(jobj.getString("Room"), jobj.getString("Chair"));
			JSONObject jres = new JSONObject(responseStr);
			return Response.status(jres.getInt("code")).entity(jres.toString()).build();				
		}
		catch (IOException e) {
			e.printStackTrace();
			return Response.status(500).entity("Error occurred during application startup. Please contact system administrator").build();
		}
		catch (SQLException e) {
			e.printStackTrace();
			return Response.status(500).entity("Error occurred during database operation while creating association. Please contact").build();
		}
		catch (NamingException e) {
			return Response.status(500).entity("Error occurred during database operation while creating association. Please contact system administrator").build();
		}
		catch (Exception e){
			return Response.status(500).entity("Error occurred. Please contact system administrator").build();
		}		
	}
	
	public Response reassociate(String room, String chair){
			
		try{
			String responseStr = dbq.delete(room, chair);
			if(responseStr.equals("deleted")){
				responseStr = dbq.associate(room, chair);
				JSONObject jres = new JSONObject(responseStr);
				return Response.status(jres.getInt("code")).entity(jres.toString()).build();
			}
			else{
				return Response.status(500).entity("Error occurred. Please contact system administrator").build();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			return Response.status(500).entity("Error occurred during application startup. Please contact system administrator").build();
		}
		catch (SQLException e) {
			e.printStackTrace();
			return Response.status(500).entity("Error occurred during database operation while reassociating chair. Please contact").build();
		}
		catch (NamingException e) {
			return Response.status(500).entity("Error occurred during database operation while reassociating chair. Please contact system administrator").build();
		}
		catch (Exception e){
			return Response.status(500).entity("Error occurred. Please contact system administrator").build();
		}	
	}
}
