import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class EvilCorpBanking {
	public static void main(String[] args){
			// Get the data from the file
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter file name");
			String fname = sc.nextLine();
			System.out.println((System.getProperty("user.dir") + File.separatorChar + fname));
			String filename = (System.getProperty("user.dir") + File.separatorChar + fname);
			Map<String, Account> accountMap = new HashMap<String, Account>();
			try{
				System.out.println("Read in list of Accounts");
				File file = new File(filename);
				if(file.exists()){
					accountMap = readLines(file);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}			
		
			// Read new transactions from user input
			List<Transaction> transactionList = new ArrayList<Transaction>();
			
			System.out.println("Welcome to Evil Corp Savings and Loan");
			System.out.println("Please create the user account(s)");
			listCurrentAccount(accountMap);
			String next = Validator.getString(sc, "Enter an account # or -1 to stop entering accounts : ");
			String regex = "\\d+";
			while(!next.equals("-1") && !next.matches(regex)){
				next = Validator.getString(sc, "Enter a valid number for acct #: ");
			}
			while(!next.equals("-1")){
				//String accountNumber = Validator.getString(sc, "Enter an account # or -1 to stop entering accounts : ");
				String name = Validator.getString(sc, "Enter the name for acct # " + next + " : ");
				double balance = Validator.getDouble(sc, "Enter the balance for acct # " + next + " : ");

				Account a = new Account(next, name, balance);
				accountMap.put(next, a);
				listCurrentAccount(accountMap);
				next = Validator.getString(sc, "Enter an account # or -1 to stop entering accounts : ");
				while(!next.equals("-1") && !next.matches(regex)){
					next = Validator.getString(sc, "Enter a valid number for acct #: ");
				}
			}
			System.out.println();

			
			// Read new account from user input

			next = Validator.getString(sc, "Enter a transaction type (Check, Debit card, Deposit or Withdrawal) or -1 to finish : ");
			while(!next.equalsIgnoreCase("check") && !next.equalsIgnoreCase("debit card") && !next.equalsIgnoreCase("deposit") && !next.equalsIgnoreCase("withdrawal") && !next.equalsIgnoreCase("-1")){
				next = Validator.getString(sc, "Enter a valid transaction type: ");
			}
			while(!next.equals("-1")){
				//String type = Validator.getString(sc, "Enter a transaction type (Check, Debit card, Deposit or Withdrawal) or -1 to finish : ");
				String accountNumber = Validator.getString(sc, "Enter the account # : ");
				while(!accountNumber.matches(regex)){
					next = Validator.getString(sc, "Enter a valid number for acct #: ");
				}
				double amount = Validator.getDouble(sc, "Enter the amount of the check:", 0, 1000000000000000000.0);
				String tmp = Validator.getString(sc, "Enter the date of the check: (please enter in MM/dd/yyyy format)");
				String regexDate = "^(1[0-2]|0?[1-9])/(3[01]|[12][0-9]|0?[1-9])/(?:[0-9]{2})?[0-9]{2}$";
				while(!tmp.matches(regexDate)){
					tmp = Validator.getString(sc, "Enter a valid date of the check: (please enter in MM/dd/yyyy format)");
				}
				SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				Date date = new Date();
				try{
					date = format.parse(tmp);
				}catch(Exception e){
					
				}
				Transaction t = new Transaction(next, accountNumber, amount, date);
				transactionList.add(t);
				next = Validator.getString(sc, "Enter a transaction type (Check, Debit card, Deposit or Withdrawal) or -1 to finish : ");
				while(!next.equalsIgnoreCase("check") && !next.equalsIgnoreCase("debit card") && !next.equalsIgnoreCase("deposit") && !next.equalsIgnoreCase("withdrawal") && !next.equalsIgnoreCase("-1")){
					next = Validator.getString(sc, "Enter a valid transaction type: ");
				}
			}

			Comparator<Transaction> dateComparator = new Comparator<Transaction>(){
				public int compare(Transaction t1, Transaction t2){
					return t1.getDate().compareTo(t2.getDate());
				}
			};

			Collections.sort(transactionList, dateComparator);

			//Map<String, Double> balanceMap = new HashMap<String, Double>();
			countBalance(transactionList, accountMap);
			listCurrentAccount(accountMap);
			printBalance(accountMap);
			
			System.out.println();
			System.out.println("Do you want to close an account? (Y/N)");
			sc = new Scanner(System.in);
			String close = sc.next();
			while(!close.equalsIgnoreCase("N")){
				listCurrentAccount(accountMap);
				String accountN = Validator.getString(sc, "Enter account number: ");
				while(!accountN.matches(regex)){
					accountN = Validator.getString(sc, "Enter a valid account number: ");
				}
				String s = closeAccount(accountMap, accountN);
				System.out.println(s);
				listCurrentAccount(accountMap);
				close = Validator.getString(sc, "Do you want to close an account? (Y/N)");
			}
			System.out.println();
			System.out.println("Finish all and save the flie.");
			// Save the data to the File
			saveToFile(accountMap, filename);
	}
	
	private static String closeAccount(Map<String, Account> accountMap, String accountN){
		if(!accountMap.containsKey(accountN)){
			return "account doesn't exist.";
		}
		Account a = accountMap.get(accountN);
		if(a.getBalance() != 0){
			return "Balance is not 0. You can't close this account.";
		}
		accountMap.remove(accountN);
		return "remove acount: " + a.getName() + " successfully!";
	}

	private static void listCurrentAccount(Map<String, Account> accountMap){
		System.out.println("*******************************************");
		for(String s : accountMap.keySet()){
			System.out.println(s + "\t" + accountMap.get(s).getName() + "\t" + String.valueOf(accountMap.get(s).getBalance()));
		}
		System.out.println("*******************************************");
	}
	
	private static void saveToFile(Map<String, Account> accountMap, String filename){
		System.out.println("Printing to a file now...");
		PrintWriter writer = null;
		try{
			writer = new PrintWriter(filename);
			System.out.println(filename);
			for(String s : accountMap.keySet()){
				//System.out.println(s + " " + accountMap.get(s).getName() + " " + String.valueOf(accountMap.get(s).getBalance()));
				writer.println(s + "/" + accountMap.get(s).getName() + "/" + String.valueOf(accountMap.get(s).getBalance()));
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		writer.close();
		System.out.println("Finish printing");
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
			if(type.equalsIgnoreCase("deposit")){
				a.makeDeposit(tmp);
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
	
	private static Map<String, Account> readLines(File file) throws Exception {
		Map<String, Account> map = new HashMap<String, Account>();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = reader.readLine();
	    while (line != null) {
	    	String[] str = line.split("/");
	    	Account a = new Account(str[0], str[1], Double.valueOf(str[2]));
	    	map.put(str[0], a);
	    	line = reader.readLine();
	    }
	    reader.close();
		return map;
	}
}