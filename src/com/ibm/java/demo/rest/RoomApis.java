package com.ibm.java.demo.rest;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import com.ibm.java.demo.db.DatabaseQuery;
import com.ibm.java.demo.exception.CustomException;
import com.ibm.java.demo.exception.InvalidDataException;
import com.ibm.java.demo.exception.InvalidResponseException;
import com.ibm.java.demo.exception.RoomException;
import com.ibm.java.demo.service.RoomManager;
import com.ibm.java.demo.validation.DataValidationCheck;;

@Path("/rooms")
public class RoomApis {

	DataValidationCheck validate = new DataValidationCheck();
	RoomManager roomManager = new RoomManager(new DatabaseQuery());

	/*
	 * Retreive Rooms on page load/refresh to populate the drop-down menu.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRooms() throws SQLException, NamingException, IOException, CustomException {

		
		JSONObject response;
		
		try{
			response = roomManager.getRoomNames();
		}catch(CustomException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
		 
		return Response.status(Status.ACCEPTED).entity(response.toString()).build();
	}

	/*
	 * Create a new room
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postRoom(String roomName) {

		try {
			validate.checkEmpty(roomName);
			validate.checkKeys(roomName, "Name");
		} catch (InvalidDataException e1) {
			return Response.status(500).entity(e1.getMessage()).build();
		}

		JSONObject response;
		try {
			response = roomManager.createRoom(roomName);
		} catch (RoomException e){
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();			
		} catch (CustomException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		} 
		return Response.ok().entity(response.toString()).build();
	}
	
	@DELETE
	@Path("/{RoomId}")
	@Consumes("APPLICATION/JSON")
	public Response delete(@PathParam("RoomId") String room){
		
		try{
			validate.checkEmpty(room);
		}catch (InvalidDataException e1) {
			return Response.status(500).entity(e1.getMessage()).build();
		}
		JSONObject response;
		try {
			response = roomManager.deleteRoom(room);
		} catch (InvalidDataException | InvalidResponseException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}		
		return Response.status(response.getInt("status")).entity(response.toString()).build();
	}
}