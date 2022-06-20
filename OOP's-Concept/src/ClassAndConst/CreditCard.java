package ClassAndConst;

public class CreditCard {
	
	private String name;
	private String limits;
	private String email;
	
	public CreditCard() {
		this("demo", "1000", "demo@gmail.com");
		System.out.println("Default Card Range");
	}

	public CreditCard(String name, String limits) {
		this(name, limits, "temp@gmail.com");
		System.out.println("Two args Card Range");
		
	}

	public CreditCard(String name, String limits, String email) {
		this.name = name;
		this.limits = limits;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public String getLimits() {
		return limits;
	}

	public String getEmail() {
		return email;
	}
	
	
	
	

}
