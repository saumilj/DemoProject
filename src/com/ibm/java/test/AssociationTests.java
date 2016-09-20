package com.ibm.java.test;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

import com.ibm.java.demo.rest.AssociationApis;

public class AssociationTests {

	//associate apis
	@Test
	public void testEmptyParam() {
		AssociationApis aapis = new AssociationApis();
		Response response = aapis.postAssociate("");
		Assert.assertEquals(response.getStatus(),500 );
	}

	//association apis
	@Test
	public void testNullParam() {

		AssociationApis aapis = new AssociationApis();
		Response response = aapis.postAssociate(null);
		Assert.assertEquals(response.getStatus(),500);
	}
}
