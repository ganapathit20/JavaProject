package com.dev.storage.cassandra.dao.model;

public class Students {
	
	private int id;
	private String name;
	private String department;
	private String nameofcollege;
	private int yearofpassing;
	
	public Students() {}
	
	public Students(int id, String name, String department, String nameofcollege, int yearofpassing) {
		super();
		this.id = id;
		this.name = name;
		this.department = department;
		this.nameofcollege = nameofcollege;
		this.yearofpassing = yearofpassing;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getNameofcollege() {
		return nameofcollege;
	}
	public void setNameofcollege(String nameofcollege) {
		this.nameofcollege = nameofcollege;
	}
	public int getYearofpassing() {
		return yearofpassing;
	}
	public void setYearofpassing(int yearofpassing) {
		this.yearofpassing = yearofpassing;
	}
	@Override
	public String toString() {
		return "Students [id=" + id + ", name=" + name + ", department=" + department + ", nameofcollege="
				+ nameofcollege + ", yearofpassing=" + yearofpassing + "]";
	}
	
	
 
}
