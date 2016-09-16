package com.ibm.java.demo.entity;

public class Chair {

	private String chairName;
	
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
}
