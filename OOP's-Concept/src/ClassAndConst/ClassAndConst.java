package ClassAndConst;

public class ClassAndConst {
	
	public static void main(String[] arg) {
		
//		Account acc = new Account();
//	
//		acc.deposite(100);
//		
//		acc.withdraw(10);
//		
//		acc.withdraw(50);
//		
//		acc.withdraw(20);
//		
//		acc.deposite(140);
		
		
		CreditCard cd = new CreditCard();
		System.out.println(cd.getLimits());
		
		CreditCard cd1 = new CreditCard("one", "10000");
		System.out.println(cd1.getLimits());
		
		
	

	}

}

