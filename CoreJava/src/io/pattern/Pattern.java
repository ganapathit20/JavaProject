package io.pattern;

public class Pattern {
	
	public static void main(String[] args) {
		
		for (int i = 0; i < 5; i++) {

			for (int j = 0; j < 5; j++) {

					System.out.print(i + "" + j + " ");
			}

			System.out.println();

		}

		System.out.println("==============================");

		for (int i = 0; i < 5; i++) {

			for (int j = 0; j < 5; j++) {
				
				if(i==j)
					System.out.print(" * ");
				else if(i==0&&j==4 || i==1&&j==3 || i==3&&j==1 || i==4&&j==0)
					System.out.print(" * ");
					
				else
					System.out.print(" "+" ");
			}

			System.out.println();

		}
	}

}
