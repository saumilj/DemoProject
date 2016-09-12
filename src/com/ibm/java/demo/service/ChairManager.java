package com.ibm.java.demo.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.ibm.java.demo.db.DatabaseQuery;
import com.ibm.java.demo.exception.CustomException;

public class ChairManager {
	
	DatabaseQuery dbq = new DatabaseQuery();

	public Response createChair(String chairName){
		
		JSONObject jobj = new JSONObject(chairName);				
		try{
			JSONObject response = dbq.createResource(jobj.getString("Name"), "Chair");
			return Response.ok().entity(response.toString()).build();
		}catch (CustomException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	public Response getChairNames() throws SQLException, NamingException, IOException, CustomException{
				
		try {
			JSONObject response = dbq.getNames("Chair");
			return Response.status(200).entity(response.toString()).build();
		} catch (CustomException e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
		
	}
}
