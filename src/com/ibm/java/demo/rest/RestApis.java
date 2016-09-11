package com.ibm.java.demo.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ibm.java.demo.db.DatabaseQuery;
import com.ibm.java.demo.service.Chair;
import com.ibm.java.demo.service.Report;
import com.ibm.java.demo.service.Room;
import com.ibm.java.demo.service.RoomChair;

@Path("/service")
public class RestApis {
	
	DatabaseQuery dbq = new DatabaseQuery();
	
	@GET
	@Path("/chair")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getChairs(){
		
		Chair chair = new Chair();
		return chair.getChairNames();
	}
	
	@GET
	@Path("/room")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRooms(){	
		
		Room room = new Room();
		return room.getRoomNames();
	}
	
	@GET
	@Path("/report")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReport() throws JsonProcessingException{
		
		Report report = new Report();
		return report.generateReport();
	}
	
	@POST
	@Path("/chair")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postChair(String chairName){	
		
		Chair chair = new Chair();
		return chair.createChair(chairName);
	}

	@POST
	@Path("/room")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postRoom(String roomName){
		
		Room room = new Room();
		return room.createRoom(roomName);
	}
	
	@POST
	@Path("/associate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postAssociate(String names){
		RoomChair rcs = new RoomChair();		
		return rcs.associate(names);
	}
	
	@DELETE
	@Path("/remove/{ChairId}/{RoomId}")
	@Consumes("APPLICATION/JSON")
	public Response deleteResource(@PathParam("ChairId") String chair, @PathParam("RoomId") String room){
		
		RoomChair rcs = new RoomChair();
		return rcs.reassociate(room, chair);			
	}
}