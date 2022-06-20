package ClassAndConst;

public class Account {
	
	private int number;
	private double balence;
	private String custName;
	private String custEmail;
	
	public void deposite(double depositeAmount) {
		this.balence+=depositeAmount;
		System.out.println("deposite to "+ depositeAmount + " done. New balence is " + this.balence);;
	}
	
	public void withdraw(double withdrawAmount) {
		
		if(this.balence - withdrawAmount <0) {
			System.out.println("Balence is "+ this.balence  + " and withdraw to " + withdrawAmount +" Insuffient balence.");
		}else {
			this.balence -= withdrawAmount;
			System.out.println("Withdraw Amount is " + withdrawAmount + " and new balence is "+ this.balence);
		}
		
	}
	
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public double getBalence() {
		return balence;
	}
	public void setBalence(double balence) {
		this.balence = balence;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustEmail() {
		return custEmail;
	}
	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}
	
	

	
	

}
