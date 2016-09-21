package com.ibm.java.demo.entity;

import java.io.Serializable;

public class Room implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
