package com.dev.storage.cassandra.dao;

import java.util.Iterator;
import java.util.Random;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.dev.storage.cassandra.Idao.IDAO;
import com.dev.storage.cassandra.connection.CassandraDBConnection;
import com.dev.storage.cassandra.dao.model.Boodskap;
import com.dev.storage.cassandra.dao.model.Students;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class CRUDoperations implements IDAO{

	private static CRUDoperations crud = new CRUDoperations();

	public static CRUDoperations getInstanceCRUD() {
		return crud;
	}

	public void insert1() {
		
		try {
			
			Boodskap boodskap = null;
			
			int i = 1;
			
			while(true) {
				
				boodskap=new Boodskap();
				
				boodskap.setRollNo(i);
				
				boodskap.setName("Boodskap " + i);
				
				ObjectMapper mapper = new ObjectMapper();

				String json = mapper.writeValueAsString(boodskap);

				StringBuilder query = new StringBuilder();

				query.append("INSERT INTO boodskap.boodskap JSON ' ").append(json + "';");

				CassandraDBConnection.getInstance().getSession().execute(query.toString());
				
				i++;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void insert() {

		try {

			int numberOfStudents=10;

			Students s = null;

			Random randam = new Random();

			for(int i=1; i<=numberOfStudents;i++) {
				
				s = new Students();	
				
				s.setId(100+i);
				s.setName("Ganapathi"+i);
				s.setDepartment("EEE"+i);
				s.setNameofcollege("KSR"+i);
				s.setYearofpassing(randam.nextInt(2020));

				//System.out.println(s);

				ObjectMapper mapper = new ObjectMapper();

				String json = mapper.writeValueAsString(s);

				StringBuilder query = new StringBuilder();

				query.append("INSERT INTO college.students JSON ' ").append(json + "';");

				CassandraDBConnection.getInstance().getSession().execute(query.toString());

				//System.out.println("Insert Query Success for : "+query.toString());
			}


		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	@Override
	public void get() {

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT JSON * FROM college.students");

		ResultSet rs = CassandraDBConnection.getInstance().getSession().execute(sql.toString());
		
		Iterator<Row> rows = rs.iterator();
		
		Gson gson = new Gson();

		while(rows.hasNext()) {

			Row r = rows.next();
			
			String newR= r.getString(0);

			Students std = gson.fromJson(newR, Students.class);// json to java object
			
		//	System.out.println(gson.toJson(newR));// String to Json String 
		}
	}

	@Override
	public void update() {

		StringBuilder sql = new StringBuilder();

		sql.append("UPDATE college.students SET name = ?, department = ?, nameofcollege = ? WHERE id = ?;");

		PreparedStatement ps = CassandraDBConnection.getInstance().getSession().prepare(sql.toString());

		BoundStatement bs = ps.bind("Srini","ECE","IIT",104);

		CassandraDBConnection.getInstance().getSession().execute(bs);

		System.out.println("Updated...");
	}

	@Override
	public void delete() {

		StringBuilder sql = new StringBuilder();

		sql.append("DELETE FROM college.students WHERE id=?;");

		PreparedStatement ps = CassandraDBConnection.getInstance().getSession().prepare(sql.toString());

		BoundStatement bs = ps.bind(113);

		CassandraDBConnection.getInstance().getSession().execute(bs);

		System.out.println("deleted...");

	}

	@Override
	public void updateByQuery(String what, String how) {
		
		StringBuilder sql = new StringBuilder();

		sql.append("UPDATE college.students SET " + what + " WHERE " + how +";");

		CassandraDBConnection.getInstance().getSession().execute(sql.toString());

		System.out.println("updateByQuery...");
		
	}

	@Override
	public void searchByQuery(String what, String how) {
		
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT JSON " + what +  "FROM college.students");

		ResultSet rs = CassandraDBConnection.getInstance().getSession().execute(sql.toString());
		
		Iterator<Row> rows = rs.iterator();
		
		Gson gson = new Gson();

		while(rows.hasNext()) {

			Row r = rows.next();
			
			String newR= r.getString(0);

			@SuppressWarnings("unused")
			Students std = gson.fromJson(newR, Students.class);// json to java object
			
		System.out.println(gson.toJson(newR));// String to Json String 
		}
	}

	@Override
	public void deleteByQuery(String what, String how) {
		// TODO Auto-generated method stub
		
	}



}
