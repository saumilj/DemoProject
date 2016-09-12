package com.ibm.java.demo.service;

import javax.ws.rs.core.Response;

import com.ibm.java.demo.db.DatabaseQuery;
import com.ibm.java.demo.exception.CustomException;

public class ReportManager {

	DatabaseQuery dbq = new DatabaseQuery();
	
	public Response generateReport() {
		
		try{
			String responseStr = dbq.getReport();
			return Response.status(200).entity(responseStr).build();
		}
		catch (CustomException e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
}
