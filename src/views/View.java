package views;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import entities.Account;
import entities.Bank;
import entities.LoanOrder;
import entities.Order;
import entities.SavingsAccount;
import entities.StandardAccount;
import entities.TransferOrder;

public class View {

	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	private static Scanner in = new Scanner(System.in);

	public static char index() {
		System.out.println("\t\t\tOpen a new account (o)");
		System.out.println("\t\t\tLogin (l)");
		System.out.println("\t\t\tManage accounts (m)");
		System.out.println("\t\t\tClose application (c)");
		System.out.println("\n");
		char input = in.next().charAt(0);
		while ((input != 'o') && (input != 'l') && (input != 'm') && (input != 'c')) {
			System.out.println("Invalid input! try again");
			input = in.next().charAt(0);
		}
		System.out.println("\n\n\n");
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
		while (accType != 's' && accType != 'd') {
			System.out.println("Invalid input! try again");
			accType = in.next().charAt(0);
		}
		if (accType == 's') {
			acc = new SavingsAccount(holderName, accountNumber, password, balance, new Date(), 0);
		} else {
			System.out.print("Trasfer limit: ");
			double transferLimit = in.nextDouble();
			System.out.print("Loan limit: ");
			double loanLimit = in.nextDouble();
			acc = new StandardAccount(holderName, accountNumber, password, balance, loanLimit, transferLimit);
		}
		System.out.println("\n\n\n");
		return acc;
	}

	public static Account login(Bank bank) {
		System.out.println("\t\t\tLogin");
		System.out.print("Account number: ");
		int accountNumber = in.nextInt();
		Account acc = bank.getAccounts().stream().filter(x -> x.getAccountNumber() == accountNumber).findFirst()
				.orElse(null);
		if (acc == null) {
			System.out.println("Account not found!");
			return null;
		}
		System.out.print("Password: ");
		in.nextLine();
		String password = in.nextLine();
		while (!acc.getPassword().contains(password) && password.length() != 6) {
			System.out.println("Wrong password! try again");
			System.out.print("Password: ");
			password = in.nextLine();
		}
		System.out.println("\n\n\n");
		return acc;
	}

	public static char accountLogged(Account acc) {
		System.out.println("\t\t\t\t\tBack (b)");
		System.out.println("Number: " + acc.getAccountNumber() + " Holder: "
		+ acc.getHolderName() + " Balance: " + acc.getBalance());
		if (acc instanceof StandardAccount) {
			System.out.println("Transfer Limit: " + ((StandardAccount) acc).getTransferLimit()
					+ " Loan limit: " + ((StandardAccount) acc).getLoanLimit());
			System.out.println("\t\t\tTransfer (t)");
			System.out.println("\t\t\tGet a loan (l)");
		}
		System.out.println("\t\t\tWithdraw (w)");
		System.out.println("\t\t\tDeposit (d)");
		System.out.println("\t\t\tOrder history (o)");
		char input = in.next().charAt(0);
		while ((input != 'b') && (input != 't') && (input != 'l') && (input != 'w') && (input != 'd')
				&& (input != 'o')) {
			System.out.println("Invalid input! try again");
			input = in.next().charAt(0);
		}
		System.out.println("\n\n\n");
		return input;
	}

	public static void transferView(Account acc, Bank bank) {
		if (!(acc instanceof StandardAccount)) {
			return;
		}
		System.out.println("TRANSFER:");
		System.out.print("Enter the account number that will receive the transfer: ");
		int accountNumber = in.nextInt();
		System.out.print("Enter the amount($) you want to transfer: ");
		double amount = in.nextDouble();
		StandardAccount accAux = (StandardAccount) acc;
		if (!accAux.transfer(bank, accountNumber, amount)) {
			System.out.println("Transfer failed!");
			System.out.println("Account not found, insufficient balance or value is above the limit");
			return;
		}
		System.out.println("Successful transfer!");
		System.out.println("\n\n\n");
	}

	public static void loanView(Account acc, Bank bank) {
		if (!(acc instanceof StandardAccount)) {
			return;
		}
		System.out.println("LOAN:");
		System.out.print("Enter the loan amount($): ");
		double loanAmount = in.nextDouble();
		System.out.println("Total amount to be paid starting 30 days from now:" + loanAmount *(1+ bank.getLoanCharge()));
		System.out.print("Enter the number of installments(1-12): ");
		int numberOfInstallments = in.nextInt();
		while(numberOfInstallments < 1 || numberOfInstallments > 12) {
			System.out.print("Invalid number of installments! try again:");
			numberOfInstallments = in.nextInt();
		}
		StandardAccount accAux = (StandardAccount) acc;
		if (!(accAux.loan(bank, loanAmount,numberOfInstallments))) {
			System.out.println("Loan failed!");
			System.out.println("Value is above the limit");
			return;
		}
		System.out.println("Successful loan! Enjoy the money!");
		System.out.println("\n\n\n");
	}

	public static void withdrawView(Account acc, Bank bank) {
		System.out.println("WITHDRAW:");
		System.out.print("Enter the amount($) you want to withdraw: ");
		double withdrawAmount = in.nextDouble();
		if(!acc.withdraw(withdrawAmount, bank)) {
			System.out.println("Withdraw failed!");
			System.out.println("Insufficient balance");
			return;
		}
		
		System.out.println("Successful Withdraw! Enjoy the money!");
		System.out.println("\n\n\n");
	}
	
	public static void depositView(Account acc) {
		System.out.println("DEPOSIT:");
		System.out.print("Enter the amount($) you want to deposit: ");
		double depositAmount = in.nextDouble();
		if(!acc.deposit(depositAmount)) {
			System.out.println("Deposit failed!");
			return;
		}
		
		System.out.println("Successful deposit!");
		System.out.println("\n\n\n");
	}

	public static void orderHistoryView(Account acc) {
		for(Order order : acc.getOrderHistory()) {
			System.out.println("ORDER DATA:");
			System.out.println("Type: " + order.getOrderType() + " Total Value: $" +String.format("%.2f", order.getTotalValue()) 
			+"\nDate: " + sdf.format(order.getDate()));
			if(order instanceof TransferOrder) {
				System.out.println("Total transferred: $" + String.format("%.2f", ((TransferOrder)order).getValueTransferred())
				+ "Account that received: " + ((TransferOrder)order).getAccountToGetPaid());
			}else if(order instanceof LoanOrder) {
				System.out.println("Loan total value: $" + String.format("%.2f", ((LoanOrder)order).getLoanValue())
				+ " Value per installment: " + String.format("%.2f", ((LoanOrder)order).getValuePerInstallment())
				+"\nInstallments: " + ((LoanOrder)order).getNumberOfPaidInstallments() + "/" +((LoanOrder)order).getNumberOfInstallments());
			}
			System.out.println();
		}
		System.out.println("\n\n\n");
	}
}
