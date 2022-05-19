package io.bmi;

import java.util.Scanner;

public class BMICal {
	
	public static void main(String[] args) {
		
		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("Enter your weight in Kg:");
			int weight = sc.nextInt();
			System.out.println("Enter your height in Cm:");
			int height = sc.nextInt();
			
			//System.out.println( Weight.weight(weight) + " " + Height.height(height));
			
			double bmi = Weight.weight(weight)/ Height.height(height);
			
			System.out.println("Your BMI " + bmi + " Kg/m2");
			
			Level.level(bmi);
		}
		
	}

}


class Height{
	
	public static double height(double h) {
	
	double htm = (h/100) * (h/100) ;

	return htm;
	}
}

class Weight{
	
	public static int weight(int weight) {
		
		return weight;
		
		}
}


class Level{
	
	public static void level(double range) {
		
		if(range <= 18) {
			System.out.println("Unter Weight...");
		}else if(range>=18 || range<=25) {
			System.out.println("Normal...");	
		}else if(range>=25 || range<=30) {
			System.out.println("Over Weight...");	
		}else {
			System.out.println("Obsent...");
		}
		
		
	}
}