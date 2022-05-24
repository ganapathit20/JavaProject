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

import io.boodskap.PlatformApiIntegrate.PlatformApiIntegrate;

@RestController
public class RestApis {

	@RequestMapping("/api")
	@ResponseBody
	public Map<String, Object> getApikey(@RequestParam("uName") String uName, @RequestParam("uPass") String uPass)
			throws Exception {

		String sql = "SELECT * FROM bsl";

		String domainKey = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		String apiKey = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

		String token = "0123456789" + "abcdefghijklmnopqrstuvxyz";

		Random random = new Random();

		int dKey = 12;

		int aKey = 16;

		int Token = 26;

		StringBuilder ak = new StringBuilder();

		StringBuilder dkey = new StringBuilder();

		StringBuilder Tokens = new StringBuilder();

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection con = DriverManager.getConnection("jdbc:mysql://192.168.1.32:3306/boodskap", "root", "example");

		Statement st = con.createStatement();

		ResultSet rs = st.executeQuery(sql);

		Map<String, Object> key = new HashMap<>();

		while (rs.next()) {

			if(rs.getString(1).equals(uName) && rs.getString(2).equals(uPass)) {

				key.put("Domain", rs.getString(1));
				
				key.put("License", "Admin License");
				
				key.put("DomainKey", rs.getString(3));
				
				key.put("Platform Data", PlatformApiIntegrate.APIIntegrate());

//				do {
//
//					for (int i = 1; i < dKey; i++) {
//
//						int index = random.nextInt(domainKey.length());
//
//						char randomChar = domainKey.charAt(index);
//
//						key.put("DomainKey", dkey.append(randomChar).toString());
//					}
//
//				} while (!rs.getString(1).contains(uName));
				

				for (int i = 1; i < aKey; i++) {

					int index = random.nextInt(apiKey.length());

					char randomChar = apiKey.charAt(index);

					key.put("Apikey", ak.append(randomChar).toString());
				}

				for (int i = 1; i < Token; i++) {

					int index = random.nextInt(token.length());

					char randomChar = token.charAt(index);

					if (i == 5 || i == 13 || i == 19)
						Tokens.append("-");
					else
						Tokens.append(randomChar);

					key.put("Token", Tokens.toString());
				}

				System.out.println("Done");

			} else {
				Map<String, Object> key1 = new HashMap<>();
				key1.put("Authentication ", "Failure");
				return key1;
				// return "woring input";
			}

		}

		return key;

	}

}
