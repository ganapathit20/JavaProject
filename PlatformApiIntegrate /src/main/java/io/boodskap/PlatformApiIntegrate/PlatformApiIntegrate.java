package io.boodskap.PlatformApiIntegrate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PlatformApiIntegrate {
	
	
	public static Map<String, Object> APIIntegrate() throws Exception {
		
		String platformAPI = "https://dev.boodskap.io/api/user/get/4e78dca0-8cc2-46fc-b5fb-d735fcf81892/mithun@gmail.com";

		StringBuilder content = new StringBuilder();  

		URL url = new URL(platformAPI);  

		URLConnection urlConnection = url.openConnection(); 

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));  
		
		String line; 

		while ((line = bufferedReader.readLine()) != null)  
		{  
			content.append(line + "\n");  
		}  
		bufferedReader.close();  
		
		System.out.println(content.toString());
		
		ObjectMapper cmapper = new ObjectMapper();
		
	      Map<String, Object> map = cmapper.readValue(  
	    		  content.toString(), new TypeReference<Map<String, Object>>() {  
          });   
		
	      for(Map.Entry<String, Object> entry : map.entrySet()) {
	    	  
	    	  System.out.println("Key : " + entry.getKey() + " <==> Value : " + entry.getValue());
	    	  
	      }
		return map;
		 
	}

}
