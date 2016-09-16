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

import org.json.JSONObject;

import com.ibm.java.demo.db.DatabaseQuery;
import com.ibm.java.demo.exception.CustomException;
import com.ibm.java.demo.exception.InvalidDataException;
import com.ibm.java.demo.exception.InvalidResponseException;
import com.ibm.java.demo.service.ChairManager;

@Path("/chairs")
public class ChairApis {
	
	DatabaseQuery dbq = new DatabaseQuery();
	
	/*
	 * Retreive chairs on page load to populate the dropdown menu
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getChairs(){
		
		ChairManager chairManager = new ChairManager();
		JSONObject response;
		try {
			response = chairManager.getChairNames();
		} catch (InvalidResponseException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.status(response.getInt("status")).entity(response.toString()).build();
	}
	
	/*
	 * Create new Chair
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postChair(String chairName){	
		
		ChairManager chairManager = new ChairManager();
		JSONObject response;
		try {
			response = chairManager.createChair(chairName);
		} catch (InvalidDataException | InvalidResponseException e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		} 
		return Response.status(response.getInt("status")).entity(response.toString()).build();
	}
	
	/*
	 * Delete an existing chair
	 */
	@DELETE
	@Path("/{ChairId}")
	@Consumes("APPLICATION/JSON")
	public Response delete(@PathParam("ChairId") String chair){		
		ChairManager chairManager = new ChairManager();
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