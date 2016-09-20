package com.ibm.java.test;

import org.hamcrest.CoreMatchers;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.ibm.java.demo.db.DatabaseQuery;
import com.ibm.java.demo.exception.InvalidResponseException;
import com.ibm.java.demo.service.ChairManager;

public class ChairManagerTest {
	
	DatabaseQuery dbq = new DatabaseQuery();
	
	//createChair
		@Test
		public void newChairInsertResponse() {
			
			ChairManager cm = new ChairManager(dbq);
			JSONObject j = new JSONObject();
			j.put("Name", "ChairUnit"+System.currentTimeMillis()/10000);
			JSONObject jobj = cm.createChair(j.toString());
			Assert.assertNotNull(jobj.getString("Id"));
		}
		
		//createChair
		@Test
		public void existingChairInsertResponse() {
			
			ChairManager cm = new ChairManager(dbq);
			JSONObject j = new JSONObject();
			j.put("Name", "ChairUnit");
			JSONObject jobj = cm.createChair(j.toString());
			Assert.assertThat(jobj.toString(), CoreMatchers.containsString("exists"));
		}
		
		
		//getChairNames
		@Test
		public void getChairs() throws InvalidResponseException {
				
			ChairManager cm = new ChairManager(dbq);
			JSONObject jobj = cm.getChairNames();
			Assert.assertEquals(jobj.getInt("status"),200);
		}


}
