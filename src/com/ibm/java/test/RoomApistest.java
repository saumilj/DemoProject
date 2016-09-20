package com.ibm.java.test;

import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.ibm.java.demo.db.DatabaseQuery;
import com.ibm.java.demo.rest.RoomApis;

public class RoomApisTest {
	
	DatabaseQuery dbq = new DatabaseQuery();

    
	@Test
	public void testNullParam() {
		
		RoomApis capis = new RoomApis();
		Response jobj = capis.postRoom(null);
		Assert.assertEquals(jobj.getStatus(),500);
	}

	
	@Test
	public void testEmptyParam() {

		RoomApis capis = new RoomApis();
		Response jobj = capis.postRoom("");
		Assert.assertEquals(jobj.getStatus(),500);
	}

	
	@Test
	public void missingNameKey() {

		JSONObject j = new JSONObject();
		j.put("test", "data");
		RoomApis capis = new RoomApis();
		Response jobj = capis.postRoom(j.toString());
		Assert.assertEquals(jobj.getStatus(),500);
	}

}
