package com.ibm.java.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ibm.java.demo.entity.Chair;

public class ChairTest {
	
	@Test
	public void testConstructor()
	{
		String name = "newChair";		
		Chair r = new Chair("newChair");
		assertEquals(r.getChairName(),name);
		
	}
	
	@Test
	public void testName()
	{
		String chairName = "test";
		Chair r = new Chair();
		r.setChairName(chairName);
		assertEquals(r.getChairName(),chairName);
	}
	
	@Test
	public void testId()
	{
		String uui = "101011";
		Chair r = new Chair();
		r.setChairId(uui);;
		assertEquals(r.getChairId(),uui);
	}

}
