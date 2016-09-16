package com.ibm.java.demo.rest;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.ibm.java.demo.db.DatabaseQuery;
import com.ibm.java.demo.exception.CustomException;
import com.ibm.java.demo.exception.InvalidDataException;
import com.ibm.java.demo.exception.InvalidResponseException;
import com.ibm.java.demo.service.RoomManager;;

@Path("/rooms")
public class RoomApis {
	
	DatabaseQuery dbq = new DatabaseQuery();
	
	/*
	 * Retreive Rooms on page load/refresh to populate the dropdwon menu.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRooms() throws SQLException, NamingException, IOException, CustomException{	
		
		RoomManager roomManager = new RoomManager();
		JSONObject response;
		try {
			response = roomManager.getRoomNames();
		} catch (InvalidResponseException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.status(response.getInt("status")).entity(response.toString()).build();
	}
	
	/*
	 * Create a new chair
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postRoom(String roomName){
		
		RoomManager roomManager = new RoomManager();
		JSONObject response;
		try {
			response = roomManager.createRoom(roomName);
		} catch (InvalidDataException | InvalidResponseException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.status(response.getInt("status")).entity(response.toString()).build();
	}
}