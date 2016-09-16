package com.ibm.java.test;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import org.hamcrest.CoreMatchers;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.ibm.java.demo.db.DatabaseQuery;
import com.ibm.java.demo.entity.Room;
import com.ibm.java.demo.exception.CustomException;
import com.ibm.java.demo.exception.RoomException;
import com.ibm.java.demo.service.RoomManager;

public class RoomManagerMocks {
	
	private DatabaseQuery test;
	@Before
	public void setUp() throws CustomException, RoomException{
		
		test = Mockito.mock(DatabaseQuery.class); 
		Room tRoom = new Room("MyRoom");
		tRoom.setRoomId("1");
		when(test.createRoom(isA(Room.class))).thenReturn(tRoom);
	}
	
	@Test
	public void testCreateRoom(){		
		String json = "{'Name':'myroom'}";
		RoomManager cm = new RoomManager(test);
		JSONObject s = cm.createRoom(json);
		Assert.assertThat(s.toString(), CoreMatchers.containsString("Room inserted successfully successfully"));
	}
	
	@Test
	public void CreateRoomTestRoomException() throws CustomException, RoomException{
		
		when(test.createRoom(isA(Room.class))).thenThrow(new RoomException());
		String json = "{'Name':'myroom'}";
		RoomManager cm = new RoomManager(test);
		JSONObject s = cm.createRoom(json);
		System.out.print(s.toString());
		Assert.assertThat(s.toString(), CoreMatchers.containsString("response"));
	}
	
	@Test
	public void CreateRoomTestCustomException() throws CustomException, RoomException{
		
		when(test.createRoom(isA(Room.class))).thenThrow(new CustomException());
		String json = "{'Name':'myroom'}";
		RoomManager cm = new RoomManager(test);
		JSONObject s = cm.createRoom(json);
		System.out.print(s.toString());
		Assert.assertThat(s.toString(), CoreMatchers.containsString("500"));
	}

}
