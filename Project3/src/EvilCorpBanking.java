import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class EvilCorpBanking {
	public static void main(String[] args){
			Map<String, Account> accountMap = new HashMap<String, Account>();
			List<Transaction> transactionList = new ArrayList<Transaction>();
			Scanner sc = new Scanner(System.in);

			System.out.println("Welcome to Evil Corp Savings and Loan");
			System.out.println("Please create the user account(s)");
			String next = Validator.getString(sc, "Enter an account # or -1 to stop entering accounts : ");
			while(!next.equals("-1")){
				//String accountNumber = Validator.getString(sc, "Enter an account # or -1 to stop entering accounts : ");
				String name = Validator.getString(sc, "Enter the name for acct # " + next + " : ");
				double balance = Validator.getDouble(sc, "Enter the balance for acct # " + next + " : ");

				Account a = new Account(next, name, balance);
				accountMap.put(next, a);
				next = Validator.getString(sc, "Enter an account # or -1 to stop entering accounts : ");
			}
			System.out.println();

			next = Validator.getString(sc, "Enter a transaction type (Check, Debit card, Deposit or Withdrawal) or -1 to finish : ");
			while(!next.equals("-1")){
				//String type = Validator.getString(sc, "Enter a transaction type (Check, Debit card, Deposit or Withdrawal) or -1 to finish : ");
				String accountNumber = Validator.getString(sc, "Enter the account # : ");
				double amount = Validator.getDouble(sc, "Enter the amount of the check:");
				String tmp = Validator.getString(sc, "Enter the date of the check: (please enter in MM/dd/yyyy format)");
				SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				Date date = new Date();
				try{
					date = format.parse(tmp);
				}catch(Exception e){

				}
				Transaction t = new Transaction(next, accountNumber, amount, date);
				transactionList.add(t);
				next = Validator.getString(sc, "Enter a transaction type (Check, Debit card, Deposit or Withdrawal) or -1 to finish : ");
			}

			Comparator<Transaction> dateComparator = new Comparator<Transaction>(){
				public int compare(Transaction t1, Transaction t2){
					return t1.getDate().compareTo(t2.getDate());
				}
			};

			Collections.sort(transactionList, dateComparator);

			//Map<String, Double> balanceMap = new HashMap<String, Double>();
			countBalance(transactionList, accountMap);
			printBalance(accountMap);
	}

	private static void countBalance(List<Transaction> list, Map<String, Account> map){
		for(Transaction t : list){
			String accountNumber = t.getAccountNumber();
			if(!map.containsKey(accountNumber)){
				continue;
			}
			Account a = map.get(accountNumber);
			double tmp = t.getAmount();
			String type = t.getType();
			if(type.equalsIgnoreCase("D")){
				a.setBalance(a.getBalance() + tmp);
			}else{
				a.setBalance(a.getBalance() - tmp);
				if(a.getBalance() < 0){
					a.setBalance(a.getBalance() - 35);
				}
			}
		}
	}

	private static void printBalance(Map<String, Account> map){
		System.out.println();
		System.out.println("Printing Balance information: ");
		for(String s : map.keySet()){
			System.out.println("The balance for account " + s + " is " + map.get(s).getFormattedBalance());
		}
	}
}