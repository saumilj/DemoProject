package com.ibm.java.test;

import javax.ws.rs.core.Response;

import org.hamcrest.CoreMatchers;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.ibm.java.demo.db.DatabaseQuery;
import com.ibm.java.demo.exception.InvalidResponseException;
import com.ibm.java.demo.rest.ChairApis;
import com.ibm.java.demo.service.ChairManager;

//Different class for apis
public class ChairApisTest {
	
	DatabaseQuery dbq = new DatabaseQuery();

    //postChair
	@Test
	public void testNullParam() throws InvalidResponseException {
		
		ChairApis capis = new ChairApis();
		Response jobj = capis.postChair(null);
		Assert.assertEquals(jobj.getStatus(),500);
	}

	//postChair
	@Test
	public void testEmptyParam() throws InvalidResponseException {

		ChairApis capis = new ChairApis();
		Response jobj = capis.postChair("");
		Assert.assertEquals(jobj.getStatus(),500);
	}

	//postChair
	@Test
	public void missingNameKey() {

		JSONObject j = new JSONObject();
		j.put("test", "data");
		ChairApis capis = new ChairApis();
		Response jobj = capis.postChair(j.toString());
		Assert.assertEquals(jobj.getStatus(),500);
	}
}
