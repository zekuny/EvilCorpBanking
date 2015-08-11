import java.text.NumberFormat;

public class Account {
	String accountNumber;
	String name;
	double balance;

	public Account() {
		accountNumber = "0000000";
		name = "";
		balance = 0.0;
	}

	public Account(String a, String n, double b) {
		accountNumber = a;
		name = n;
		balance = b;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getFormattedBalance() {
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		return currency.format(this.getBalance());
	}
	
	public void makeDeposit(double n){
		balance += n;
	}
}