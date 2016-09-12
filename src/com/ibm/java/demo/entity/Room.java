package com.ibm.java.demo.entity;

public class Room {

	private String roomName;
	public Room(String roomName){
		
		this.roomName = roomName;
	}
	
	public String getRoomName(){
		
		return roomName;
	}
	
	public void setRoomName(String roomName){
		
		this.roomName = roomName;
	}
}
