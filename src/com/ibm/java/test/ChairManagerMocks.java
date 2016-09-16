package com.ibm.java.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import org.hamcrest.CoreMatchers;
import org.json.JSONObject;

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
	public void testCreateChair(){		
		String json = "{'Name':'mychair'}";
		ChairManager cm = new ChairManager(test);
		JSONObject s = cm.createChair(json);
		Assert.assertThat(s.toString(), CoreMatchers.containsString("Chair inserted successfully successfully"));
	}

}
