package com.maths.io.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApis {

	@RequestMapping("/api")
	@ResponseBody
	public Map<String, String> getApikey(@RequestParam("uName") String uName, @RequestParam("uPass") String uPass) throws Exception {

		String sql = "SELECT * FROM bsl";

		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

		StringBuilder sb = new StringBuilder();

		Random random = new Random();

		int length = 12;

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/boodskap", "root", "example");

		Statement st = con.createStatement();

		ResultSet rs = st.executeQuery(sql);
		
		Map<String, String> key = new HashMap<>();

		while (rs.next()) {

			if (rs.getString(1).contains(uName) && rs.getString(2).contains(uPass)) {

				for (int i = 1; i < length; i++) {

					int index = random.nextInt(alphabet.length());

					char randomChar = alphabet.charAt(index);

					//Apikey
					
					sb.append(randomChar);
					
					//Token

//					if (i % 5 == 0)
//						sb.append("-");
//					else
//						sb.append(randomChar);
					
					
					key.put("Apikey", sb.toString());
				}

			} else {
				Map<String, String> key1 = new HashMap<>();
				return key1;
				//return "woring input";
			}

		}

		return key;

	}

}
