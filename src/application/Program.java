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
		
		
		Bank bank = Controller.readFromDatabase();
		
		Controller.manageViews(bank);
		
		Controller.writeToDatabase(bank);
	
	}
}
