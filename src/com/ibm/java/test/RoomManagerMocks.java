package com.ibm.java.test;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.hamcrest.CoreMatchers;
import org.json.JSONObject;
import org.junit.After;
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
	//private Connection con = null;
	Room tRoom;
	@Before
	public void setUp() throws CustomException, RoomException, SQLException{
		
		//con = DriverManager.getConnection("jdbc:mysql://localhost:3306/TestDemoProject", "root", "sumisam");
		test = Mockito.mock(DatabaseQuery.class); 
		tRoom = new Room("MyRoom");
		tRoom.setRoomId("1");
		when(test.createRoom(isA(Room.class))).thenReturn(tRoom);
	}
	
	@Test
	public void CreateRoomTestRoomException() throws CustomException, RoomException{
		
		when(test.createRoom(isA(Room.class))).thenThrow(new RoomException());
		String json = "{'Name':'myroom'}";
		RoomManager cm = new RoomManager(test);
		JSONObject s = cm.createRoom(json);
		System.out.print(s.toString());
		Assert.assertThat(s.toString(), CoreMatchers.containsString("500"));
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
	@After
	public void tearDown() throws CustomException, RoomException, SQLException{
		
		test=null;
		//con.close(); 
	}

}
