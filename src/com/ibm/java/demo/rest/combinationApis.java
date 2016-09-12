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
import com.ibm.java.demo.service.ReportManager;
import com.ibm.java.demo.service.AssociationManager;;

@Path("/service")
public class CombinationApis {
	
	DatabaseQuery dbq = new DatabaseQuery();
	
	@GET
	@Path("/report")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReport() throws JsonProcessingException{
		ReportManager reportManager = new ReportManager();
		return reportManager.generateReport();
	}
	
	@POST
	@Path("/associate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postAssociate(String names){
		AssociationManager associationManager = new AssociationManager();		
		return associationManager.associate(names);
	}
	
	@DELETE
	@Path("/remove/{ChairId}/{RoomId}")
	@Consumes("APPLICATION/JSON")
	public Response deleteResource(@PathParam("ChairId") String chair, @PathParam("RoomId") String room){		
		AssociationManager associationManager = new AssociationManager();
		return associationManager.reassociate(room, chair);			
	}
}