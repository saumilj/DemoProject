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

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ibm.java.demo.db.DatabaseQuery;
import com.ibm.java.demo.exception.CustomException;
import com.ibm.java.demo.exception.InvalidDataException;
import com.ibm.java.demo.exception.InvalidResponseException;
import com.ibm.java.demo.service.ReportManager;
import com.ibm.java.demo.service.AssociationManager;;

@Path("/associate")
public class AssociationApis {
	
	DatabaseQuery dbq = new DatabaseQuery();
	
	/*
	 * Create an association of a chair to a room
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postAssociate(String names){
		AssociationManager associationManager = new AssociationManager();		
		JSONObject response;
		try {
			response = associationManager.associate(names);
		} catch (InvalidDataException | InvalidResponseException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		} 
		return Response.status(response.getInt("status")).entity(response.toString()).build();		
		
	}
	
	/*
	 * Reassociate a chair to another room requested by client
	 */
	@DELETE
	@Path("/reassociate/{ChairId}/{RoomId}")
	@Consumes("APPLICATION/JSON")
	public Response deleteResource(@PathParam("ChairId") String chair, @PathParam("RoomId") String room){		
		AssociationManager associationManager = new AssociationManager();
		JSONObject response;
		try {
			response = associationManager.reassociate(room, chair);
		} catch (InvalidDataException | InvalidResponseException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}	
		return Response.status(response.getInt("status")).entity(response.toString()).build();
	}
	
	@POST
	@Path("/schema")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newSchema(String json) throws CustomException{
		AssociationManager associationManager = new AssociationManager();		
		JSONObject response;
		try {
			response = associationManager.changeAssociation(json);
		} catch (InvalidDataException | InvalidResponseException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.status(response.getInt("status")).entity(response.toString()).build();		
		
	}
}