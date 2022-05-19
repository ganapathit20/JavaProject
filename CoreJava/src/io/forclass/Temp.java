package io.forclass;

public class Temp {

	static {

		System.out.println("Static block");
	}

	{
		System.out.println("Instance block");
	}

}
