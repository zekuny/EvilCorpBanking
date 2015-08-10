import java.util.Date;

public class Transaction {
	String type;
	String accountNumber;
	double amount;
	Date date;

	public Transaction() {
		type = "";
		accountNumber = "0000000";
		amount = 0.0;
		date = new Date();
	}

	public Transaction(String t, String a, double am, Date d) {
		type = t;
		accountNumber = a;
		amount = am;
		date = d;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}