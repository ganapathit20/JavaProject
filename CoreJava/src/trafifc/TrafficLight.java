package trafifc;

public class TrafficLight {

	public static void main(String[] args) throws InterruptedException {

		singleLight(true);

	}

	public static void singleLight(boolean isOn) throws InterruptedException {
		
		if(!isOn)
			System.out.println("No Signal");
		
		do {
			
			if(isOn) {
			System.out.println("Green signal");
			
			Thread.sleep(7000);  
			
			System.out.println("Orange Signal");
			
			Thread.sleep(3000);  
			
			System.out.println("Red Signal");
			
			Thread.sleep(9000); 
			}
		}while(isOn);

		

	}

}
