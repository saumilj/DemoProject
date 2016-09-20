package com.ibm.java.test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import com.ibm.java.demo.entity.Room;

public class RoomTest {
	
	@Test
	public void testConstructor()
	{
		String name = "newRoom";		
		Room r = new Room("newRoom");
		assertEquals(r.getRoomName(),name);
		
	}
	
	@Test
	public void testName()
	{
		String roomName = "test";
		Room r = new Room();
		r.setRoomName(roomName);
		assertEquals(r.getRoomName(),roomName);
	}
	
	@Test
	public void testId()
	{
		String uui = "101011";
		Room r = new Room();
		r.setRoomId(uui);;
		assertEquals(r.getRoomId(),uui);
	}

}
