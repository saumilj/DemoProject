package com.ibm.java.demo.service;

import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.ibm.java.demo.db.DatabaseQuery;
import com.ibm.java.demo.exception.CustomException;

public class RoomManager {
	
DatabaseQuery dbq = new DatabaseQuery();
	
	public Response createRoom(String roomName) {
		
		JSONObject jobj = new JSONObject(roomName);		
		try{
			JSONObject response = dbq.createResource(jobj.getString("Name"), "Room");
			return Response.ok().entity(response.toString()).build();
		}catch (CustomException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
public Response getRoomNames(){
		
		try {
			JSONObject response = dbq.getNames("Room");
			return Response.status(200).entity(response.toString()).build();
		} catch (CustomException e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
		
	}
}
