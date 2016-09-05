package com.ibm.java.demo.rest;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.json.JSONException;
import org.json.JSONObject;

@Path("/service")
public class RestApis {
	
	DatabaseQuery dbq = new DatabaseQuery();
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getChairs(){
			
		Sample f = new Sample("Test","success");
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter(); // for serializing and printing in proper format
		String json = null;
				
		try {
			json = ow.writeValueAsString(f);
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}		
		return Response.ok(json).build();	
	}
	
	@PUT
	@Path("/chair")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postChair(String f){
				
		String response = null;
		JSONObject jobj = new JSONObject(f);
		String chair = jobj.getString("message");
		System.out.print(jobj.getString("message"));
		try {
			response = dbq.CreateChair(chair);
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		return Response.ok().entity("done").build();
	}
	
	@POST
	@Path("/room")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postRoom(String f) throws FileNotFoundException, JSONException, SQLException, NamingException{
				
		String response1 = null;
		String response2 = null;
		
		JSONObject jobj = new JSONObject(f);
		String name = jobj.getString("Name");
		String attribute = jobj.getString("Attribute");
		
		//response1 = dbq.CreateRoom(room);
		
		//test implementation for changing query received from properties file.
		response2 = dbq.createResource(name, attribute);
		
		System.out.print(response2);
		return Response.ok().entity("done").build();
	}
	
	@DELETE
	@Consumes("APPLICATION/JSON")
	public Response deleteResource(){
		return Response.ok().entity("connection test").build();	
	}
}
