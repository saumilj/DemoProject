package com.ibm.java.demo.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.ibm.java.demo.db.DatabaseQuery;

public class Room {
	
DatabaseQuery dbq = new DatabaseQuery();
	
	public Response createRoom(String roomName) {
		
		JSONObject jobj = new JSONObject(roomName);		
		try{
			String responseStr = dbq.createResource(jobj.getString("Name"), "Room");
			JSONObject jres = new JSONObject(responseStr);
			return Response.status(jres.getInt("code")).entity(jres.toString()).build();
		}
		catch (IOException e) {
			e.printStackTrace();
			return Response.status(500).entity("Error occurred during application startup. Please contact system administrator").build();
		}
		catch (SQLException e) {
			e.printStackTrace();
			return Response.status(500).entity("Error occurred during database operation while trying to create a room. Please contact").build();
		}
		catch (NamingException e) {
			return Response.status(500).entity("Error occurred during database operation while trying to create a room. Please contact system administrator").build();
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
	

}
