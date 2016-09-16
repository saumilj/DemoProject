package com.ibm.java.test;

import org.hamcrest.CoreMatchers;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import com.ibm.java.demo.db.DatabaseQuery;
import com.ibm.java.demo.exception.CustomException;

public class DbAssociateTests {
	
	DatabaseQuery dbq = new DatabaseQuery();
	
	@Test	
	public void checkStringParam() throws CustomException{
		dbq.Dev = true;	
		JSONObject jobj = dbq.associateChairToRoom("roomdbtest", "chairdbtest");
		Assert.assertThat(jobj.toString(), CoreMatchers.containsString("Association exists"));		
	}	
}
