package com.dev.storage.cassandra.Idao;

public interface IDAO {
	
	public void insert();
	
	public void update();
	
	public void delete();
	
	public void get();
	
	public void updateByQuery(String what, String how);
	
	public void searchByQuery(String what, String how);
	
	public void deleteByQuery(String what, String how);

}
