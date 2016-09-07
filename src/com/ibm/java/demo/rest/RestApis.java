package com.ibm.java.demo.rest;

import java.io.FileNotFoundException;
import java.io.IOException;
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
import javax.ws.rs.core.Response.ResponseBuilder;

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
		int status;
		//ResponseBuilder res2 = null;
		
		try {
				res = dbq.getNames("Chair");
				status=200;
			
		} catch (FileNotFoundException | SQLException | NamingException e) {
			status=404;
			e.printStackTrace();
			//res2.entity("response:"+e.getMessage());
			
		}		
		// verify
		return Response.status(status).entity(res).build();	
	}
	
	@GET
	@Path("/room")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRooms(){
			
		String res = null;
		try {
			res = dbq.getNames("Room");
			return Response.status(200).entity(res).build();
		} 
		catch (FileNotFoundException e){e.printStackTrace(); return Response.status(404).entity("IO Exception").build();}	
		catch(SQLException e){e.printStackTrace();return Response.status(501).entity("SQL Exception").build();}
		catch(NamingException e){e.printStackTrace();return Response.status(501).entity("Naming Exception").build();}
	}
	
	@GET
	@Path("/report")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReport() throws JsonProcessingException{
		
		String res = null;
		int status;
		try {
				res = dbq.getReport();
				status=200; 
			
		} catch (FileNotFoundException | SQLException | NamingException e) {
			// TODO Auto-generated catch block
			status=404;
			e.printStackTrace();
		}			
		return Response.status(status).entity(res).build();
	}
	
	@POST
	@Path("/chair")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postChair(String f) throws IOException{
				
		String response = null;	
		JSONObject jobj = new JSONObject(f);
		//Single function to create new Chair/Room
		try{
			response = dbq.createResource(jobj.getString("Name"), "Chair");
			JSONObject jres = new JSONObject(response);
			return Response.status(jres.getInt("code")).entity(jres.toString()).build();	
		}
		catch (IOException e) {e.printStackTrace();return Response.status(501).entity("IO Exception caught").build();}
		catch (Exception e) {e.printStackTrace();return Response.status(501).entity("Unidentified Exception caught").build();}
		
//		JSONObject res = new JSONObject();
//		res.put("response","print");
//		//System.out.print(response);
//		return Response.status(status).entity(res.toString()).build();
	}
	
	@POST
	@Path("/room")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postRoom(String f) throws IOException{
				
		String response = null;
		JSONObject jobj = new JSONObject(f);
		try{
			response = dbq.createResource(jobj.getString("Name"), "Room");
			JSONObject jres = new JSONObject(response);
			return Response.status(jres.getInt("code")).entity(jres.toString()).build();	
		}
		catch (IOException e) {e.printStackTrace();return Response.status(501).entity("IO Exception caught").build();}
		catch (Exception e) {e.printStackTrace();return Response.status(501).entity("Unidentified Exception caught").build();}
		
//		JSONObject res = new JSONObject();
//		res.put("response","print");
//		System.out.print(response);
//		return Response.status(status).entity(res.toString()).build();
	}
	
	@POST
	@Path("/associate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postAssociate(String f){
				
		String response = null;	
		JSONObject jobj = new JSONObject(f);
		try{
			response = dbq.associate(jobj.getString("Room"), jobj.getString("Chair"));
			JSONObject jres = new JSONObject(response);
			return Response.status(jres.getInt("code")).entity(jres.toString()).build();				
		}
		catch (IOException e) {e.printStackTrace();return Response.status(501).entity("IO Exception caught").build();}
		catch (Exception e) {e.printStackTrace();return Response.status(501).entity("Unidentified Exception caught").build();}		
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