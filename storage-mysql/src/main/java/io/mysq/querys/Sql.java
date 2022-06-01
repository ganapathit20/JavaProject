package io.mysq.querys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Sql {
	
	public static void connection() {
		
		try {
			
	String sql = "CREATE TABLE BSL ( uName varchar(255), uPass varchar(255) );";
	
	
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection con = DriverManager.getConnection("jdbc:mysql://admin.c0f25kuqwmed.us-west-1.rds.amazonaws.com:3306/admin", "admin", "admin123");
		
		System.out.println("connected...");
		
		PreparedStatement ps = con.prepareStatement(sql);
		
		boolean rs = ps.execute();
		
		System.out.println("done");
		
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}

}
