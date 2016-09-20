package com.ibm.java.test;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import org.hamcrest.CoreMatchers;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.ibm.java.demo.db.DatabaseQuery;
import com.ibm.java.demo.entity.Chair;
import com.ibm.java.demo.exception.ChairException;
import com.ibm.java.demo.exception.CustomException;
import com.ibm.java.demo.service.ChairManager;

public class ChairManagerMocks {
	
	private DatabaseQuery test;
	@Before
	public void setUp() throws CustomException, ChairException{
		
		test = Mockito.mock(DatabaseQuery.class); 
		Chair tChair = new Chair("MyChair");
		tChair.setChairId("1");
		when(test.createChair(isA(Chair.class))).thenReturn(tChair);
	}
	
	@Test
	public void CreateChairTestChairException() throws CustomException, ChairException{
		
		when(test.createChair(isA(Chair.class))).thenThrow(new ChairException());
		String json = "{'Name':'existingchair'}";
		ChairManager cm = new ChairManager(test);
		JSONObject s = cm.createChair(json);
		System.out.print(s.toString());
		Assert.assertThat(s.toString(), CoreMatchers.containsString("500"));
	}
	
	@Test
	public void CreateChairTestCustomException() throws CustomException, ChairException{
		
		when(test.createChair(isA(Chair.class))).thenThrow(new CustomException());
		String json = "{'Name':'existingchair'}";
		ChairManager cm = new ChairManager(test);
		JSONObject s = cm.createChair(json);
		System.out.print(s.toString());
		Assert.assertThat(s.toString(), CoreMatchers.containsString("500"));
	}
	
	@After
	public void teardown(){
		
		test = null;
		//con.close();
	}

}
