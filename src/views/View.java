package views;

import java.util.Date;
import java.util.Scanner;

import entities.Account;
import entities.Bank;
import entities.SavingsAccount;
import entities.StandardAccount;

public class View {

	private static Scanner in;

	public static char index() {

		in = new Scanner(System.in);

		System.out.println("\t\t\tOpen a new account (o)");
		System.out.println("\t\t\tLogin (l)");
		System.out.println("\t\t\tManage accounts (m)");
		System.out.println("\t\t\tClose application (c)");
		char input = in.next().charAt(0);
		while ((input != 'o') && (input != 'l') && (input != 'm') && (input != 'c')) {
			System.out.println("Invalid input! try again");
			input = in.next().charAt(0);
		}
		return input;
	}

	public static Account openAccount(Bank bank) {
		Account acc;
		int accountNumber = 1000 + bank.getNumberOfAccounts();
		System.out.println("Enter Account data:");
		System.out.print("Holder name: ");
		in.nextLine();
		String holderName = in.nextLine();
		System.out.print("Password (6 digits): ");
		String password = in.nextLine();
		while (!password.matches("[0-9]+") || password.length() > 6) {
			System.out.println("Invalid password! try again");
			System.out.print("Password (6 digits): ");
			password = in.nextLine();
		}
		System.out.print("Is there an initial deposit (y/n)? ");
		char aux = in.next().charAt(0);
		Double balance = 0.0;
		if (aux == 'y') {
			System.out.print("Enter initial deposit value: ");
			balance = in.nextDouble();
		}
		System.out.print("Choose an account type Savings(s) or Standard(d): ");
		char accType = in.next().charAt(0);
		while(accType != 's' && accType != 'd') {
			System.out.println("Invalid input! try again");
			accType = in.next().charAt(0);
		}
		if(accType == 's') {
			acc = new SavingsAccount(holderName,accountNumber,password, balance, new Date(),0);
		}
		else {
			System.out.print("Trasfer limit: ");
			double transferLimit = in.nextDouble();
			System.out.print("Loan limit: ");
			double loanLimit = in.nextDouble();
			acc = new StandardAccount(holderName,accountNumber,password, balance,loanLimit,transferLimit);
		}
		return acc;
	}

}
