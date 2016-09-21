package com.ibm.java.test;

import javax.ws.rs.core.Response;

import org.hamcrest.CoreMatchers;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.ibm.java.demo.db.DatabaseQuery;
import com.ibm.java.demo.exception.CustomException;
import com.ibm.java.demo.exception.InvalidResponseException;
import com.ibm.java.demo.exception.RoomException;
import com.ibm.java.demo.rest.RoomApis;
import com.ibm.java.demo.service.RoomManager;

public class RoomManagerTests {
	
	// Negative Test create Room Method
	DatabaseQuery dbq = new DatabaseQuery();

    //postRoom
	@Test
	public void testNullParam() throws InvalidResponseException {
		
		RoomApis capis = new RoomApis();
		Response jobj = capis.postRoom(null);
		Assert.assertEquals(jobj.getStatus(),500);
	}

	//postRoom
	@Test
	public void testEmptyParam() throws InvalidResponseException {

		RoomApis capis = new RoomApis();
		Response jobj = capis.postRoom("");
		Assert.assertEquals(jobj.getStatus(),500);
	}

	//postRoom
	@Test
	public void missingNameKey() {

		JSONObject j = new JSONObject();
		j.put("test", "data");
		RoomApis capis = new RoomApis();
		Response jobj = capis.postRoom(j.toString());
		Assert.assertEquals(jobj.getStatus(),500);
	}

	//createRoom
	@Test
	public void newRoomInsertResponse() throws CustomException, RoomException {
		
		RoomManager cm = new RoomManager(dbq);
		JSONObject j = new JSONObject();
		j.put("Name", "RoomUnit"+System.currentTimeMillis()/1000);
		JSONObject jobj = cm.createRoom(j.toString());
		Assert.assertThat(jobj.toString(), CoreMatchers.containsString("Room inserted successfully"));
	}
	
	//createRoom
	@Test
	public void existingRoomInsertResponse() throws CustomException, RoomException {
		
		RoomManager cm = new RoomManager(dbq);
		JSONObject j = new JSONObject();
		j.put("Name", "RoomUnit");
		JSONObject jobj = cm.createRoom(j.toString());
		Assert.assertThat(jobj.toString(), CoreMatchers.containsString("exists"));
	}
	
	//getRoomNames
	@Test
	public void getRooms() throws CustomException {
			
		RoomManager cm = new RoomManager(dbq);
		JSONObject jobj = cm.getRoomNames();
		Assert.assertEquals(jobj.getInt("status"),200);
	}
}
