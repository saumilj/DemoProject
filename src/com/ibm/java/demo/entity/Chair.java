package com.ibm.java.demo.entity;

import java.io.Serializable;

public class Chair implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String chairName;
	private String Id;
	
	public Chair(){}
	
	public Chair(String chairName){
		
		this.chairName = chairName;
	}
	
	public String getChairName(){
		
		return chairName;
	}
	
	public void setChairName(String chairName){
		
		this.chairName = chairName;
	}
	
	public void setChairId(String Id){
		
		this.Id = Id;
	}
	
	public String getChairId(){
		
		return Id;
	}
}
