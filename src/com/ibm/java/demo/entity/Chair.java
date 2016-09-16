package com.ibm.java.demo.entity;

public class Chair {

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
