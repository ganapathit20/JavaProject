package com.maths.io.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.simple.JSONObject;

public class MysqlQuerys {
	
	public static Connection getConnection() throws Exception {
	
		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/boodskap", "root", "example");
		
		return con;
		
	}

	public static String insert() throws Exception {

		if(getConnection()!=null)
		{	
		String sql = "INSERT INTO BSL(personID,name,address,city,roll) VALUES(?,?,?,?,?)";
		
		PreparedStatement ps = getConnection().prepareStatement(sql);
		
		ps.setInt(1, 121012);
		ps.setString(2, "Gopi");
		ps.setString(3, "Ammapet");
		ps.setString(4, "Salem");
		ps.setString(5, "Java Developer");
		
		ps.execute();
		
		getConnection().close();
		
		return "Data Insert done...";
		
		}else {
			System.out.println("Connection null...");
		}
		return null;
		 
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject readBSL() throws Exception {
		
		String sql = "SELECT * FROM BSL";
		
		Statement st = getConnection().createStatement();
		
		ResultSet rs = st.executeQuery(sql);
		
		JSONObject r = null;
		
		while(rs.next()) {
			
			 r = new JSONObject();
			r.put("PersonID", rs.getInt("personID"));
			r.put("Name", rs.getString(1));
			r.put("Address", rs.getString(2));
			r.put("City", rs.getString(3));
			r.put("Roll", rs.getString(4));
			
		}
		return r;
		
	}

}
