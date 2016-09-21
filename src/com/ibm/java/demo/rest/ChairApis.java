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
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import com.ibm.java.demo.db.DatabaseQuery;
import com.ibm.java.demo.validation.DataValidationCheck;
import com.ibm.java.demo.exception.ChairException;
import com.ibm.java.demo.exception.CustomException;
import com.ibm.java.demo.exception.InvalidDataException;
import com.ibm.java.demo.exception.InvalidResponseException;
import com.ibm.java.demo.service.ChairManager;

@Path("/chairs")
public class ChairApis {
	
	DataValidationCheck validate = new DataValidationCheck();
	ChairManager chairManager = new ChairManager(new DatabaseQuery());
	/*
	 * Retreive chairs on page load to populate the dropdown menu
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getChairs() {
				
		JSONObject response;
		try {
			response = chairManager.getChairNames();
		} catch (CustomException e) {
			return Response.status(500).entity(e.getMessage()).build();
		}	
		return Response.status(Status.ACCEPTED).entity(response.toString()).build();
	}
	
	/*
	 * Create new Chair
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postChair(String chairName){	
		
		try {
			validate.checkEmpty(chairName);
			validate.checkKeys(chairName, "Name");
		} catch (InvalidDataException e1) {
			return Response.status(500).entity(e1.getMessage()).build();
		}
				
		JSONObject response;
		try {
			response = chairManager.createChair(chairName);
		} catch (ChairException e) {
			
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		} catch (CustomException e){
			
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.ok().entity(response.toString()).build();
	}
	
	/*
	 * Delete an existing chair
	 */
	@DELETE
	@Path("/{ChairId}")
	@Consumes("APPLICATION/JSON")
	public Response delete(@PathParam("ChairId") String chair){
		
		try{
			validate.checkEmpty(chair);
		}catch (InvalidDataException e1) {
			return Response.status(500).entity(e1.getMessage()).build();
		}
		
		JSONObject response;
		try {
			response = chairManager.deleteChair(chair);
		} catch (InvalidDataException | InvalidResponseException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}		
		return Response.status(response.getInt("status")).entity(response.toString()).build();
	}
}