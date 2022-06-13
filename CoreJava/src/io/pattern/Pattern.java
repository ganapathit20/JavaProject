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
		
		
		for(int row = 0; row < 3; row++) {

			for(int col = 0; col < 3; col++) {

				if((row==1 && col==0) || (row==0 && col==1) || (row==1 && col==2) || (row==2 && col==1)) {
					System.out.print("*"+""+"*"+ " ");
				}else
					System.out.print(row+""+col+ " ");
			}
			System.out.println();
		}
		
		
		for(int row = 0; row < 3; row++) {

			for(int col = 3; col >row; col--) {

				System.out.print("*");
			}
			System.out.println();
		}
		
		for(int row = 0; row < 3; row++) {

			for(int col = 0; col <=row; col++) {

				System.out.print("*");
			}
			System.out.println();
		}
	}

}
