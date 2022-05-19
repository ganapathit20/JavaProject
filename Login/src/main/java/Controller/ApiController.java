package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;


public class ApiController {


	public void checkAuthentication(HttpServletRequest req) {

		try {
			String sql = "SELECT * FROM boodskap";

			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/boodskap", "root", "example");

			System.out.println("connected...");

			Statement ps = con.createStatement();

			ResultSet rs=ps.executeQuery(sql);

			while(rs.next()) {

				String u = rs.getString("userId");
				String p = rs.getString("pass");

				System.out.println(u +" "+ p);

			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
