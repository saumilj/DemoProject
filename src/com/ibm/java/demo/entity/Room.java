package com.ibm.java.demo.entity;

public class Room {

	private String roomName;
	private String Id;

	public Room() {
	}

	public Room(String roomName) {

		this.roomName = roomName;
	}

	public String getRoomName() {

		return roomName;
	}

	public void setRoomName(String roomName) {

		this.roomName = roomName;
	}

	public void setRoomId(String Id) {

		this.Id = Id;
	}

	public String getRoomId() {

		return Id;
	}

}
