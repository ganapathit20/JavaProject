package Inheritance;

public class Persion extends Car{
	
private String name;
	
	public Persion() {}

	public Persion(String name, String cName, String color) {
		super(cName, color);
		this.name = name;
	}
	
	public void move(int speed) {
		System.out.println("Persion moving car "+ speed+ " Km");
	}
	
	public void drive() {
		System.out.println(name + " drive the car.");
		//super.move(50);
		move(50);
	}
	
	public String getName() {
		return name;
	}

}
