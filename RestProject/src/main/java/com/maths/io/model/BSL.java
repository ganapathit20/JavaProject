package com.maths.io.model;

public class BSL {
	
	private Integer personID;
	private String name;
	private String address;
	private String city;
	private String roll;
	
	public BSL() {}

	public BSL(Integer personID, String name, String address, String city, String roll) {
		super();
		this.personID = personID;
		this.name = name;
		this.address = address;
		this.city = city;
		this.roll = roll;
	}



	public Integer getPersonID() {
		return personID;
	}

	public void setPersonID(Integer personID) {
		this.personID = personID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRoll() {
		return roll;
	}

	public void setRoll(String roll) {
		this.roll = roll;
	}
	
	
	

}
