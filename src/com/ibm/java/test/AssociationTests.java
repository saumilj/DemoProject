package com.ibm.java.test;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;
import com.ibm.java.demo.exception.InvalidDataException;
import com.ibm.java.demo.exception.InvalidResponseException;
import com.ibm.java.demo.rest.AssociationApis;

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
}
