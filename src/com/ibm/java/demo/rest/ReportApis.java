package com.ibm.java.demo.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ibm.java.demo.db.DatabaseQuery;
import com.ibm.java.demo.exception.InvalidResponseException;
import com.ibm.java.demo.service.ReportManager;

@Path("/report")
public class ReportApis {
	
DatabaseQuery dbq = new DatabaseQuery();
	
	/*
	 * Generate a report showing what room has which chairs
	 */
	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReport() throws JsonProcessingException{
		ReportManager reportManager = new ReportManager();
		JSONObject response;
		try {
			response = reportManager.generateReport();
		} catch (InvalidResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.status(response.getInt("status")).entity(response.toString()).build();
	}
}
