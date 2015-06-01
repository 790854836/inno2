package com.topcoder.innovate.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PosMsg implements Serializable {
	
	private double latitude;
	private double longitude;
	private String name;
	private String address;
	
	public String getName(){
		return name;
	}
	
	public String getAddress(){
		return address;
	}
	
	public double getLatitude(){
		return latitude;
	}
	
	public double getLongitude(){
		return longitude;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public void setLatitude(double latitude){
		this.latitude = latitude;
	}
	
	public void setLongitude(double longitude){
		this.longitude = longitude;
	}
}
