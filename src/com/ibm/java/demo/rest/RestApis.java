package com.ibm.java.demo.rest;

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

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getChairs(){
			
		Floor f = new Floor("text","success");
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
		System.out.print(jobj.getString("message"));

		return Response.ok().entity("done done").build();
	}
	
	@DELETE
	@Consumes("APPLICATION/JSON")
	public Response deleteResource(){
		return Response.ok().entity("connection here").build();	
	}
}
