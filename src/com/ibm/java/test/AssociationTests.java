package com.ibm.java.test;

import static org.junit.Assert.fail;

import org.hamcrest.CoreMatchers;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.ibm.java.demo.exception.InvalidDataException;
import com.ibm.java.demo.exception.InvalidResponseException;
import com.ibm.java.demo.service.AssociationManager;
import com.ibm.java.demo.service.ChairManager;

public class AssociationTests {

	// Negative tests for associate method
	@Test
	public void testEmptyParam() throws InvalidResponseException, InvalidDataException {
		AssociationManager am = new AssociationManager();
		JSONObject jobj = am.associate("");
		Assert.assertThat(jobj.toString(), CoreMatchers.containsString("Data entered is not valid"));
	}

	@Test
	public void testNullParam() throws InvalidDataException, InvalidResponseException {

		AssociationManager am = new AssociationManager();
		JSONObject jobj = am.associate(null);
		Assert.assertThat(jobj.toString(), CoreMatchers.containsString("Data entered is not valid"));
	}

	// Check if param is in json format
	
	
	@Test
	public void statusKeyMissingfromJson() throws InvalidResponseException {

		AssociationManager am = new AssociationManager();
		try {
			JSONObject j = new JSONObject();
			j.put("test", "data");
			j.put("response", "data2");
			am.associate(j.toString());
			fail("should throw exception");
		} catch (InvalidDataException e) {
			e.printStackTrace();
			// should catch this exception
		}
	}

	@Test
	public void responseKeyMissingfromJson() throws InvalidResponseException {

		AssociationManager am = new AssociationManager();
		try {
			JSONObject j = new JSONObject();
			j.put("test", "data");
			j.put("status", "data2");
			am.associate(j.toString());
			fail("should throw exception");
		} catch (InvalidDataException e) {
			e.printStackTrace();
			// should catch this exception
		}
	}
	
}
