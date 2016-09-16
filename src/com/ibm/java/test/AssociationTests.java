package com.ibm.java.test;

import static org.junit.Assert.fail;

import javax.ws.rs.core.Response;

import org.hamcrest.CoreMatchers;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.ibm.java.demo.exception.InvalidDataException;
import com.ibm.java.demo.exception.InvalidResponseException;
import com.ibm.java.demo.rest.AssociationApis;
import com.ibm.java.demo.service.AssociationManager;
import com.ibm.java.demo.service.ChairManager;

public class AssociationTests {

	//associate apis
	@Test
	public void testEmptyParam() throws InvalidResponseException, InvalidDataException {
		AssociationApis aapis = new AssociationApis();
		Response response = aapis.postAssociate("");
		Assert.assertEquals(response.getStatus(),500 );
	}

	//association apis
	@Test
	public void testNullParam() throws InvalidDataException, InvalidResponseException {

		AssociationApis aapis = new AssociationApis();
		Response response = aapis.postAssociate(null);
		Assert.assertEquals(response.getStatus(),500);
	}

	// Check if param is in json format

}
