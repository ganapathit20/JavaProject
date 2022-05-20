package com.maths.io.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApis {

	@RequestMapping("/api")
	@ResponseBody
	public String getApikey(
			@RequestParam("uName") String uName,
			@RequestParam("pass") String pass
			) throws Exception {
		
		String sql = "SELECT * FROM boodskap";

		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				+ "0123456789" + "abcdefghijklmnopqrstuvxyz";

		StringBuilder sb = new StringBuilder();

		Random random = new Random();

		int length = 16;
		
		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bds", "root", "example");

		Statement st = con.createStatement();

		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next()) {
			
			if(rs.getString(1).contains(uName)&&rs.getString(2).contains(pass)) {
			
				for(int i = 0; i < length; i++) {

					int index = random.nextInt(alphabet.length());
					
					char randomChar = alphabet.charAt(index);
					
					sb.append(randomChar);

				}
				
			}else {
				return "woring input";
			}

		}

		String Apikey = sb.toString();
	
		return	"ApiKey = > "+Apikey;

	}

}
