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

import com.ibm.java.demo.db.DatabaseQuery;
import com.ibm.java.demo.exception.CustomException;
import com.ibm.java.demo.service.RoomManager;;

@Path("/service")
public class RoomApis {
	
	DatabaseQuery dbq = new DatabaseQuery();
	
	@GET
	@Path("/room")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRooms() throws SQLException, NamingException, IOException, CustomException{	
		
		RoomManager roomManager = new RoomManager();
		return roomManager.getRoomNames();
	}
	
	@POST
	@Path("/room")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postRoom(String roomName){
		
		RoomManager roomManager = new RoomManager();
		return roomManager.createRoom(roomName);
	}
}