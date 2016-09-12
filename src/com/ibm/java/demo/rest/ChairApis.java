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
import com.ibm.java.demo.service.ChairManager;

@Path("/service")
public class ChairApis {
	
	DatabaseQuery dbq = new DatabaseQuery();
	
	@GET
	@Path("/chair")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getChairs() throws SQLException, NamingException, IOException, CustomException{
		
		ChairManager chairManager = new ChairManager();
		return chairManager.getChairNames();
	}
	
	@POST
	@Path("/chair")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postChair(String chairName){	
		
		ChairManager chairManager = new ChairManager();
		return chairManager.createChair(chairName);
	}
}