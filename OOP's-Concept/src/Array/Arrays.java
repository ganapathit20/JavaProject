package Array;

import java.util.Scanner;

public class Arrays {
	
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws Exception {

		sortArray();
		
		int[] numbers = getInteger(5);

		for(int i=0; i<numbers.length; i++) {
			System.out.println("Elements "+i+ " = " +numbers[i]);
		}

		System.out.println("Avg is " + avg(numbers));


	}

	public static int[] getInteger(int number) {

		System.out.println("enter 5 numbers \r");

		int[] array = new int[number];

		for(int i=0; i<array.length; i++) {
			array[i] =sc.nextInt() ;
		}

		return array;
	}


	public static double avg(int[] avgArray) {

		int sum = 0;
		for(int k=0; k<avgArray.length; k++) {
			sum+=avgArray[k];	
		}

		return (double) sum /(double) avgArray.length;

	}
	
	
	public static void sortArray() {
		
		int[] numbers = {12,98,21,10,77};

		boolean flag = true;

		while(flag) {

			flag = false;

			for(int i=0; i<numbers.length-1; i++) {
				
				//greatest number if(numbers[i] < numbers[i+1])

				if(numbers[i] > numbers[i+1]) { // smallest number

					int tem = numbers[i];

					numbers[i] = numbers[i+1];

					numbers[i+1]=tem;	

					flag = true;
				}

			}

		}
		for(int i=0; i<numbers.length; i++) {

			System.out.println(numbers[i]);
		}
	}

}
