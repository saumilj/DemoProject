package com.ibm.java.test;

import static org.junit.Assert.*;

import org.hamcrest.CoreMatchers;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.ibm.java.demo.db.*;
import com.ibm.java.demo.exception.CustomException;

public class DbGetNames {
	
	DatabaseQuery dbq = new DatabaseQuery();
	
	@Test	
	public void checkStringParam() throws CustomException{
		dbq.Dev = true;	
		JSONObject jobj = dbq.associateChairToRoom("roomdbtest", "chairdbtest");
		Assert.assertThat(jobj.toString(), CoreMatchers.containsString("Association exists"));		
	}
	
}
