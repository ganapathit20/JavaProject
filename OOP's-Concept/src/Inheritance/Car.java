package Inheritance;

public class Car {
	
	private String cName;
	private String color;
	private int wheels=4;
	private long capacity=250;
	private int millage=15;
	
	public Car() {
		
	}
	
	public Car(String cName, String color) {
		this.cName = cName;
		this.color = color;
	}
	
	public void move(int speed) {
		System.out.println("Car is Moving at " +speed+ " KM");
	}

	public String getcName() {
		return cName;
	}

	public String getColor() {
		return color;
	}

	public int getWheels() {
		return wheels;
	}

	public long getCapacity() {
		return capacity;
	}

	public int getMillage() {
		return millage;
	}

}
