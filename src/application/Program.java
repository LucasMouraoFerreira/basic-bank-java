package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import controller.Controller;
import entities.Account;
import entities.Bank;
import entities.LoanOrder;
import entities.Order;
import entities.SavingsAccount;
import entities.enums.OrderType;

public class Program {

	public static void main(String[] args) throws NumberFormatException, ParseException {
		
		Locale.setDefault(Locale.US);
		final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		
		Bank bank = Controller.readFromDatabase();
		
		/*Account acc = new SavingsAccount("Lucas Mourão", 1000, "055555", 5000.0, sdf.parse("10/08/2015 20:10:15") , 0);
		SavingsAccount acc1 = (SavingsAccount)acc;
		acc1.updateIncome(bank);
		Order order1 = new Order(1000, OrderType.DEPOSIT, new Date(), 100.0);
		LoanOrder order2 = new LoanOrder(1000, OrderType.LOAN, new Date(), 300.0, 360.0, 3, 0, 120.0);

		acc.addOrder(order1);
		acc.addOrder(order2);
		
		bank.addAccount(acc1);*/
		
		System.out.println(bank);
		for(Account acc4 : bank.getAccounts()) {
			System.out.println(acc4);
			for(Order order : acc4.getOrderHistory()) {
				System.out.println(order);
			}
		}

		Controller.writeToDatabase(bank);
	
	}
}
