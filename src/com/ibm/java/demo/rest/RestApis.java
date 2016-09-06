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
import javax.ws.rs.QueryParam;
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
	@Path("/chair")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getChairs(@QueryParam("chair") String chairName){
		
		String res = null;
		try {
				res = dbq.getNames("Chair");
			
		} catch (FileNotFoundException | SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return Response.ok(res).build();	
	}
	
	@GET
	@Path("/room")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRooms(){
			
		String res = null;
		try {
			res = dbq.getNames("Room");
		} catch (FileNotFoundException | SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return Response.ok(res).build();	
	}
	
	@POST
	@Path("/chair")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postChair(String f){
				
		String response = null;	
		JSONObject jobj = new JSONObject(f);
		String name = jobj.getString("Name");
		String attribute = jobj.getString("Attribute");
		
		//Single function to create new Chair/Room
		try{
			response = dbq.createResource(name, attribute);
		}
		catch(FileNotFoundException | JSONException | SQLException | NamingException e){
			e.printStackTrace();
		}
		
		JSONObject res = new JSONObject();
		res.put("response","print");
		System.out.print(response);
		return Response.ok().entity(res.toString()).build();
	}
	
	@POST
	@Path("/room")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postRoom(String f){
				
		String response = null;
		
		JSONObject jobj = new JSONObject(f);
		String name = jobj.getString("Name");
		String attribute = jobj.getString("Attribute");
		
		//Single function to create new Chair/Room
		try{
			response = dbq.createResource(name, attribute);
		}
		catch(FileNotFoundException | JSONException | SQLException | NamingException e){
			e.printStackTrace();
		}
		
		JSONObject res = new JSONObject();
		res.put("response","print");
		System.out.print(response);
		return Response.ok().entity(res.toString()).build();
	}
	
	@POST
	@Path("/associate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postAssociate(String f){
				
		String response = null;
		
		JSONObject jobj = new JSONObject(f);
		String room = jobj.getString("Room");
		String chair = jobj.getString("Chair");
		System.out.print("room: "+room);
		System.out.print("chair: "+chair);
		
		//Single function to create new Chair/Room
		try{
			response = dbq.associate(room, chair);
		}
		catch(FileNotFoundException | JSONException | SQLException | NamingException e){
			e.printStackTrace();
		}
		
		JSONObject res = new JSONObject();
		res.put("response","done");
		System.out.print(response);
		return Response.ok().entity(res.toString()).build();
	}
	
	@DELETE
	@Consumes("APPLICATION/JSON")
	public Response deleteResource(){
		return Response.ok().entity("connection test").build();	
	}
}


//@PUT
//@Path("/chair")
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
//public Response postChair(String f){
//			
//	String response = null;
//	JSONObject jobj = new JSONObject(f);
//	String chair = jobj.getString("message");
//	System.out.print(jobj.getString("message"));
//	try {
//		response = dbq.CreateChair(chair);
//	} catch (NamingException e1) {
//		e1.printStackTrace();
//	}
//	return Response.ok().entity("done").build();
//}

//@GET
//@Path("/room/{room}")
//@Produces(MediaType.APPLICATION_JSON)
//public Response getRoom(@QueryParam("room") String roomName){
//		
//	String res = null;
//	try {
//		res = dbq.getRoom(roomName);
//	} catch (FileNotFoundException | SQLException | NamingException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}			
//	return Response.ok(res).build();	
//}


//@GET
//@Produces(MediaType.APPLICATION_JSON)
//public Response getChairs(){
//		
//	Sample f = new Sample("Test","success");
//	ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter(); // for serializing and printing in proper format
//	String json = null;
//			
//	try {
//		json = ow.writeValueAsString(f);
//	} catch (JsonProcessingException e) {
//		
//		e.printStackTrace();
//	}		
//	return Response.ok(json).build();	
//}
//