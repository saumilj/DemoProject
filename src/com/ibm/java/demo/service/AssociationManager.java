package com.ibm.java.demo.service;

import com.ibm.java.demo.db.DatabaseQuery;
import com.ibm.java.demo.exception.CustomException;

import javax.naming.NamingException;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;

public class AssociationManager {
	
	DatabaseQuery dbq = new DatabaseQuery();
	
	public Response associate(String names){
		
		JSONObject jobj = new JSONObject(names);
		
		try{
			JSONObject response = dbq.associateChairToRoom(jobj.getString("Room"), jobj.getString("Chair"));
			return Response.status(200).entity(response.toString()).build();				
		}
		catch (CustomException e){
			//jsonobject
			return Response.status(500).entity(e.getMessage()).build();
		}		
	}
	
	public Response reassociate(String room, String chair){
			
		try{
			String responseStr = dbq.delete(room, chair);
			if(responseStr.equals("deleted")){
				JSONObject response = dbq.associateChairToRoom(room, chair);
				return Response.ok().entity(response.toString()).build();
			}
			else{
				return Response.status(500).entity("Error occurred. Please contact system administrator").build();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			return Response.status(500).entity("Error occurred during application startup. Please contact system administrator").build();
		}
		catch (SQLException e) {
			e.printStackTrace();
			return Response.status(500).entity("Error occurred during database operation while reassociating chair. Please contact").build();
		}
		catch (NamingException e) {
			return Response.status(500).entity("Error occurred during database operation while reassociating chair. Please contact system administrator").build();
		}
		catch (Exception e){
			return Response.status(500).entity("Error occurred. Please contact system administrator").build();
		}	
	}
}
