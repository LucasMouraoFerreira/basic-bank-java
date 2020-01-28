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
import exceptions.BankException;
import exceptions.UIException;

public class View {

	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	private static Scanner in = new Scanner(System.in);
	
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static char indexView() throws UIException {
		System.out.println("\t\t\tOpen a new account (o)");
		System.out.println("\t\t\tLogin (l)");
		System.out.println("\t\t\tManage accounts (m)");
		System.out.println("\t\t\tClose application (c)");
		System.out.println("\n");
		char input = in.next().charAt(0);
		if ((input != 'o') && (input != 'l') && (input != 'm') && (input != 'c')) {
			clearScreen();
			throw new UIException("Invalid command!");
		}
		clearScreen();
		return input;
	}

	public static Account openAccountView(Bank bank) throws BankException {
		Account acc;
		int accountNumber = 1000 + bank.getNumberOfAccounts();
		System.out.println("Enter Account data:");
		System.out.print("Holder name: ");
		in.nextLine();
		String holderName = in.nextLine();
		System.out.print("Password (6 digits): ");
		String password = in.nextLine();
		System.out.print("Choose an account type Savings(s) or Standard(d): ");
		char accType = in.next().charAt(0);
		while (accType != 's' && accType != 'd') {
			System.out.println("Invalid input! try again");
			accType = in.next().charAt(0);
		}
		if (accType == 's') {
			acc = new SavingsAccount(holderName, accountNumber, password, new Date(), 0);
		} else {
			System.out.print("Trasfer limit: ");
			double transferLimit = in.nextDouble();
			System.out.print("Loan limit: ");
			double loanLimit = in.nextDouble();
			acc = new StandardAccount(holderName, accountNumber, password, loanLimit, transferLimit);
		}
		System.out.print("Is there an initial deposit (y/n)? ");
		char aux = in.next().charAt(0);
		Double deposit = 0.0;
		if (aux == 'y') {
			System.out.print("Enter initial deposit value: ");
			deposit = in.nextDouble();
			acc.deposit(deposit);
		}
		System.out.println("Your account number: " + accountNumber);
		System.out.println("Account Created!");
		System.out.println("\nPress \"Enter\" to leave");
		in.nextLine();
		in.nextLine();
		clearScreen();
		return acc;
	}

	public static Account loginView(Bank bank) throws BankException {
		System.out.println("\t\t\tLogin");
		System.out.print("Account number: ");
		int accountNumber = in.nextInt();
		Account acc = bank.getAccounts().stream().filter(x -> x.getAccountNumber() == accountNumber).findFirst()
				.orElse(null);
		if (acc == null) {
			throw new BankException("Account not found");
		}
		System.out.print("Password: ");
		in.nextLine();
		String password = in.nextLine();
		if (!acc.getPassword().equals(password)) {
			throw new BankException("Invalid password!");
		}
		clearScreen();
		return acc;
	}

	public static char accountLoggedView(Account acc) throws UIException {
		System.out.println("\t\t\t\t\tBack (b)\n");
		System.out.println("Number: " + acc.getAccountNumber() + " Holder: "
		+ acc.getHolderName() + " Balance: $" + String.format("%.2f", acc.getBalance())  + "\n");
		if (acc instanceof StandardAccount) {
			System.out.println("Transfer Limit: $" + String.format("%.2f", ((StandardAccount) acc).getTransferLimit())
					+ " Loan limit: $" + String.format("%.2f", ((StandardAccount) acc).getLoanLimit()) + "\n");
			System.out.println("\t\t\tTransfer (t)");
			System.out.println("\t\t\tGet a loan (l)");
		}
		System.out.println("\t\t\tWithdraw (w)");
		System.out.println("\t\t\tDeposit (d)");
		System.out.println("\t\t\tOrder history (o)");
		char input = in.next().charAt(0);
		if ((input != 'b') && (input != 't') && (input != 'l') && (input != 'w') && (input != 'd')
				&& (input != 'o')) {
			throw new UIException("Invalid command");
		}
		clearScreen();
		return input;
	}

	public static void transferView(Account acc, Bank bank) throws BankException {
		if (!(acc instanceof StandardAccount)) {
			return;
		}
		System.out.println("TRANSFER:");
		System.out.print("Enter the account number that will receive the transfer: ");
		int accountNumber = in.nextInt();
		System.out.print("Enter the amount($) you want to transfer: ");
		double amount = in.nextDouble();
		StandardAccount accAux = (StandardAccount) acc;
		accAux.transfer(bank, accountNumber, amount);
		System.out.println("Successful transfer!\nPress \"Enter\" to leave");
		in.nextLine();
		in.nextLine();
		clearScreen();
	}

	public static void loanView(Account acc, Bank bank) throws BankException {
		System.out.println("LOAN:");
		System.out.print("Enter the loan amount($): ");
		double loanAmount = in.nextDouble();
		System.out.println("Total amount to be paid starting 30 days from now: " + loanAmount *(1+ bank.getLoanCharge()));
		System.out.print("Enter the number of installments(1-12): ");
		int numberOfInstallments = in.nextInt();
		StandardAccount accAux = (StandardAccount) acc;
		accAux.loan(bank, loanAmount,numberOfInstallments);
		System.out.println("Successful loan! Enjoy the money!\nPress \"Enter\" to leave");
		in.nextLine();
		in.nextLine();
		clearScreen();
	}

	public static void withdrawView(Account acc, Bank bank) throws BankException {
		System.out.println("WITHDRAW:");
		System.out.print("Enter the amount($) you want to withdraw: ");
		double withdrawAmount = in.nextDouble();
		acc.withdraw(withdrawAmount, bank);
		System.out.println("Successful Withdraw! Enjoy the money!\nPress \"Enter\" to leave");
		in.nextLine();
		in.nextLine();
		clearScreen();
	}
	
	public static void depositView(Account acc) throws BankException {
		System.out.println("DEPOSIT:");
		System.out.print("Enter the amount($) you want to deposit: ");
		double depositAmount = in.nextDouble();
		acc.deposit(depositAmount); 		
		System.out.println("Successful deposit!\nPress \"Enter\" to leave");
		in.nextLine();
		in.nextLine();
		clearScreen();
	}

	public static void orderHistoryView(Account acc) {
		for(Order order : acc.getOrderHistory()) {
			System.out.println("ORDER DATA:");
			System.out.println("Type: " + order.getOrderType() + " Total Value: $" +String.format("%.2f", order.getTotalValue()) 
			+"\nDate: " + sdf.format(order.getDate()));
			if(order instanceof TransferOrder) {
				System.out.println("Total transferred: $" + String.format("%.2f", ((TransferOrder)order).getValueTransferred())
				+ " Account that received: " + ((TransferOrder)order).getAccountToGetPaid());
			}else if(order instanceof LoanOrder) {
				System.out.println("Loan total value: $" + String.format("%.2f", ((LoanOrder)order).getLoanValue())
				+ " Value per installment: $" + String.format("%.2f", ((LoanOrder)order).getValuePerInstallment())
				+"\nInstallments: " + ((LoanOrder)order).getNumberOfPaidInstallments() + "/" +((LoanOrder)order).getNumberOfInstallments());
			}
			System.out.println();
		}
		System.out.println("\nPress \"Enter\" to leave");
		in.nextLine();
		in.nextLine();
		clearScreen();
	}

	public static char manageBankView() throws UIException {
		System.out.println("\t\t\t\t\tBack (b)");
		System.out.println("Change charges value (c)");
		System.out.println("Delete account (d)");
		System.out.println("Account information (i)");
		char input = in.next().charAt(0);
		if ((input != 'b') && (input != 'c') && (input != 'd') && (input != 'i')) {
			throw new UIException("Invalid Command");
		}
		clearScreen();
		return input;
	}

	public static void changeChargesView(Bank bank) throws BankException {
		System.out.println("CHANGE CHARGES");
		System.out.println("Do you want to change the loan charge? (y/n)");
		char input = in.next().charAt(0);
		if(input == 'y') {
			System.out.print("Enter new value: ");
			double loanCharge = in.nextDouble();
			bank.setLoanCharge(loanCharge);
		}
		System.out.println("Do you want to change the transfer charge? (y/n)");
		input = in.next().charAt(0);
		if(input == 'y') {
			System.out.print("Enter new value: ");
			double transferCharge = in.nextDouble();
			bank.setTransferCharge(transferCharge);
		}
		System.out.println("Do you want to change the withdraw charge? (y/n)");
		input = in.next().charAt(0);
		if(input == 'y') {
			System.out.print("Enter new value: ");
			double withdrawCharge = in.nextDouble();
			bank.setWithdrawCharge(withdrawCharge);
		}
		System.out.println("Do you want to change the savings account income? (y/n)");
		input = in.next().charAt(0);
		if(input == 'y') {
			System.out.print("Enter new value: ");
			double income = in.nextDouble();
			bank.setSavingsAccountIncome(income);;
		}
		System.out.println("Charges changed!");
		System.out.println("\nPress \"Enter\" to leave");
		in.nextLine();
		in.nextLine();
		clearScreen();
		
	}

	public static void deleteAccountView(Bank bank) throws BankException {
		System.out.println("DELETE ACCOUNT");
		System.out.print("Enter account number: ");
		int accountNumber = in.nextInt();
		Account acc = bank.getAccounts().stream().filter(x -> x.getAccountNumber() == accountNumber).findFirst()
				.orElse(null);
		bank.removeAccount(acc);
		System.out.println("Account removed\nPress \"Enter\" to leave");
		in.nextLine();
		in.nextLine();
		clearScreen();
	}

	public static void accountInformationView(Bank bank) {
		System.out.println("ACCOUNT INFORMATION");
		System.out.print("Enter account number: ");
		int accountNumber = in.nextInt();
		Account acc = bank.getAccounts().stream().filter(x -> x.getAccountNumber() == accountNumber).findFirst()
				.orElse(null);
		if(acc == null) {
			System.out.println("Account not found!");
			System.out.println("\nPress \"Enter\" to leave");
			in.nextLine();
			in.nextLine();
			clearScreen();
			return;
		}
		System.out.println("Holder: " + acc.getHolderName() + " -- Balance: " + String.format("%.2f", acc.getBalance()));
		if(acc instanceof StandardAccount) {
			System.out.println("Account Type: Standard" );
		}else {
			System.out.println("Account Type: Savings" );
		}
		System.out.println("\nPress \"Enter\" to leave");
		in.nextLine();
		in.nextLine();
		clearScreen();
	}
}
