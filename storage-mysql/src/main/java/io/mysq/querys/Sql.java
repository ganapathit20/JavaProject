package io.mysq.querys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Sql {
	
	public static void connection() {
		
		try {
			
	String sql = "CREATE TABLE BSL ( PersonID int, Name varchar(255), Address varchar(255), City varchar(255), Roll varchar(255));";
	
	
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/boodskap", "root", "example");
		
		System.out.println("connected...");
		
		PreparedStatement ps = con.prepareStatement(sql);
		
		boolean rs = ps.execute();
		
		System.out.println("done");
		
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}

}
