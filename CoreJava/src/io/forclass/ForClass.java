package io.forclass;

public class ForClass {
	
	public static void main(String[] args) throws Exception {
		
		try{
			Class.forName("io.forclass.Temp"); // run only static blocks
			
			Class.forName("io.forclass.Temp").newInstance(); // run both static and instance
	
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
		
		
	}

}

