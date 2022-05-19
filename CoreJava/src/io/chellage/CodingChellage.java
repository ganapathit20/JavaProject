package io.chellage;

import java.util.HashMap;
import java.util.Map;

public class CodingChellage {
	
	
	public static void main(String[] args) {
		
		//String reversal
				String name = "mithunraj";

				char[] rName = name.toCharArray();

				for(int i=rName.length -1; i>=0;  i--) {

					//System.out.print(rName[i]);

				}

				//Vowel
				for(int i=0; i<rName.length;  i++) {

					if((rName[i]=='a' || rName[i]=='e') || rName[i]=='i' || rName[i]=='o' || rName[i]=='u') {
						//	System.out.print(rName[i]);
					}


				}

				//Palindrome no ex o/p 515 ok 675 not ok

				int number = 515, tem=0, rem=0, check;
				
				check = number;
				
				while(number>0) {

					rem=number%10;

					tem=(tem*10)+rem;

					number=number/10;
				}

//				if(check==tem)
//					System.out.println("same");
//				else
//					System.out.println("no");
				
			
				//Check array is Containe odd number
				
				int numbers[] = {1,2,3,4,5,6};
				
				for(int i=0; i<numbers.length; i++) {
					
					if(numbers[i]%2==1) {
						
						//System.out.print(numbers[i]);
						
					}
					
				}
				
				
			//Remove White space in string
				
				String newName= "  gan apa thi t  ";
				
				System.out.println(newName.trim());
				
				char[] tem1 = newName.toCharArray();

				for(int i=0; i<tem1.length;  i++) {

					if(!Character.isWhitespace(tem1[i])) {
						//System.out.print(tem1[i]);
					}

				}
			//Count Repect letter
				String str1 = "abcdABCDabcd";

				char[] chars = str1.toCharArray();

				Map<Character, Integer> charsCount = new HashMap<>();

				for(char c : chars) {
					if(charsCount.containsKey(c)) {
						charsCount.put(c, charsCount.get(c)+1);
					}else
						charsCount.put(c, 1);
				}

				//System.out.println(charsCount);
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
	}

}
