package com.ibm.java.demo.rest;

import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postChairs(String f){
				
		JSONObject jobj = new JSONObject(f);
		String chair = jobj.getString("message");
		System.out.print(jobj.getString("message"));
		try {
			dbq.postData(chair);
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		return Response.ok().entity("done").build();
	}
	
	@DELETE
	@Consumes("APPLICATION/JSON")
	public Response deleteResource(){
		return Response.ok().entity("connection test").build();	
	}
}
