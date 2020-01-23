package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import entities.Account;
import entities.Bank;
import entities.LoanOrder;
import entities.Order;
import entities.SavingsAccount;
import entities.StandardAccount;
import entities.TransferOrder;
import entities.enums.OrderType;
import views.View;

public class Controller {
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public static Bank readFromDatabase() throws NumberFormatException, ParseException {

		double trasferCharge = 7.00; // absolute
		double loanCharge = 0.20; // percentage
		double savingsAccountIncome = 0.01; // percentage
		double withdrawCharge = 5.00; // absolute
		Bank bank = new Bank(trasferCharge, withdrawCharge, loanCharge, savingsAccountIncome);

		if (new File("./database_bank.txt").exists()) { // READ BANK DATA
			try (BufferedReader br = new BufferedReader(new FileReader("./database_bank.txt"))) {
				String line = br.readLine();
				if (line != null) {
					String splitVect[] = line.split(";");
					bank.setTrasferCharge(Double.parseDouble(splitVect[0]));
					bank.setWithdrawCharge(Double.parseDouble(splitVect[1]));
					bank.setLoanCharge(Double.parseDouble(splitVect[2]));
					bank.setSavingsAccountIncome(Double.parseDouble(splitVect[3]));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (new File("./database_accounts.txt").exists()) { // READ ACCOUNTS DATA

			try (BufferedReader br = new BufferedReader(new FileReader("./database_accounts.txt"))) {
				String line;
				while ((line = br.readLine()) != null) {
					String splitVect[] = line.split(";");
					if (splitVect[4].contains("SAVINGS")) {
						Account acc = new SavingsAccount(splitVect[0], Integer.parseInt(splitVect[1]), splitVect[2],
								Double.parseDouble(splitVect[3]), sdf.parse(splitVect[5]),
								Integer.parseInt(splitVect[6]));
						SavingsAccount acc1 = (SavingsAccount) acc;
						acc1.updateIncome(bank); // updates based on the date it runs
						bank.addAccount(acc1);
					} else if (splitVect[4].contains("STANDARD")) {
						Account acc = new StandardAccount(splitVect[0], Integer.parseInt(splitVect[1]), splitVect[2],
								Double.parseDouble(splitVect[3]), Double.parseDouble(splitVect[5]),
								Double.parseDouble(splitVect[6]));
						StandardAccount acc1 = (StandardAccount) acc;
						acc1.updateLoans(); // updates based on the date it runs
						bank.addAccount(acc1);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (new File("./database_orderHistory.txt").exists()) { // READ ACCOUNTS HISTORY DATA
			try (BufferedReader br = new BufferedReader(new FileReader("./database_orderHistory.txt"))) {
				String line;
				while ((line = br.readLine()) != null) {
					String splitVect[] = line.split(";");
					int accNumber = Integer.parseInt(splitVect[0]);
					Account acc = bank.getAccounts().stream().filter(x -> x.getAccountNumber() == accNumber).findFirst()
							.orElse(null);
					if (acc != null) {
						if (splitVect[1].contains("TRANSFER")) {
							Order order = new TransferOrder(accNumber, OrderType.TRANSFER, sdf.parse(splitVect[2]),
									Double.parseDouble(splitVect[3]), Double.parseDouble(splitVect[4]),
									Integer.parseInt(splitVect[5]));
							acc.addOrder(order);
						} else if (splitVect[1].contains("DEPOSIT")) {
							Order order = new Order(accNumber, OrderType.DEPOSIT, sdf.parse(splitVect[2]),
									Double.parseDouble(splitVect[3]));
							acc.addOrder(order);
						} else if (splitVect[1].contains("WITHDRAW")) {
							Order order = new Order(accNumber, OrderType.WITHDRAW, sdf.parse(splitVect[2]),
									Double.parseDouble(splitVect[3]));
							acc.addOrder(order);
						} else if (splitVect[1].contains("LOAN")) {
							Order order = new LoanOrder(accNumber, OrderType.LOAN, sdf.parse(splitVect[2]),
									Double.parseDouble(splitVect[3]), Double.parseDouble(splitVect[4]),
									Integer.parseInt(splitVect[5]), Integer.parseInt(splitVect[6]),
									Double.parseDouble(splitVect[7]));
							acc.addOrder(order);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return bank;

	}

	public static void writeToDatabase(Bank bank) {

		try (BufferedWriter bw = new BufferedWriter(new FileWriter("./database_bank.txt"))) { // WRITE BANK DATA TO
																								// DATABASE
			bw.write(bank.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		try (BufferedWriter bw = new BufferedWriter(new FileWriter("./database_accounts.txt"))) { // WRITE ACCOUNTS DATA
																									// TO DATABASE

			List<Account> accounts = bank.getAccounts();
			for (Account acc : accounts) {
				bw.write(acc.toString());
				bw.newLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		try (BufferedWriter bw = new BufferedWriter(new FileWriter("./database_orderHistory.txt"))) { // WRITE ACCOUNTS
																										// DATA TO
																										// DATABASE

			List<Account> accounts = bank.getAccounts();
			for (Account acc : accounts) {
				List<Order> orders = acc.getOrderHistory();
				for (Order order : orders) {
					bw.write(order.toString());
					bw.newLine();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void manageApplication(Bank bank) {
		while (true) {
			char aux = View.index();
			if (aux == 'o') {
				bank.addAccount(View.openAccount(bank));
			} else if (aux == 'l') {
				Account acc = View.login(bank);
				Controller.manageAccountLogged(acc, bank);
			} else if (aux == 'm') {
				
			} else {
				return;
			}
		}
	}

	public static void manageAccountLogged(Account acc, Bank bank) {
		char input = View.accountLogged(acc);
		while (input != 'b') {
			switch (input) {
			case 't':
				View.transferView(acc, bank);
				break;
			case 'l':
				View.loanView(acc, bank);
				break;
			case 'w':
				View.withdrawView(acc, bank);
				break;
			case 'd':
				View.depositView(acc);
				break;
			case 'o':
				View.orderHistoryView(acc);
				break;
			}
			input = View.accountLogged(acc);
		}
	}
}
