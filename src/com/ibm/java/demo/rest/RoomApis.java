package com.ibm.java.demo.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ibm.java.demo.db.DatabaseQuery;
import com.ibm.java.demo.service.Room;

@Path("/service")
public class RoomApis {
	
	DatabaseQuery dbq = new DatabaseQuery();
	
	@GET
	@Path("/room")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRooms(){	
		
		Room room = new Room();
		return room.getRoomNames();
	}
	
	@POST
	@Path("/room")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postRoom(String roomName){
		
		Room room = new Room();
		return room.createRoom(roomName);
	}
}