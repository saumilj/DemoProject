package com.ibm.java.test;

import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.ibm.java.demo.db.DatabaseQuery;
import com.ibm.java.demo.exception.InvalidResponseException;
import com.ibm.java.demo.rest.RoomApis;

public class RoomApistest {
	
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

}
