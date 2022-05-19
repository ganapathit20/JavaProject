package com.dev.storage.cassandra.dao.model;

public class Boodskap {
	
	private int rollNo;
	private String name;
	
	public Boodskap() {}
	
	public Boodskap(int rollNo, String name) {
		super();
		this.rollNo = rollNo;
		this.name = name;
	}
	public int getRollNo() {
		return rollNo;
	}
	public void setRollNo(int rollNo) {
		this.rollNo = rollNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Boodskap [rollNo=" + rollNo + ", name=" + name + "]";
	}
	
	

}
