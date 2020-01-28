package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.enums.OrderType;
import exceptions.BankException;

public class Account {
	
	
	private String holderName;
	private Integer accountNumber;
	protected Double balance;
	private List<Order> orderHistory = new ArrayList<>();
	private String password;
	
	public Account(String holderName, Integer accountNumber, String password ,Double balance) throws BankException {
		this.holderName = holderName;
		this.accountNumber = accountNumber;
		this.balance = balance;
		setPassword(password);
	}

	public Account(String holderName, Integer accountNumber, String password) throws BankException {
		this.holderName = holderName;
		this.accountNumber = accountNumber;
		setPassword(password);
		balance = 0.0;
	}
	
	
	public void setPassword(String password) throws BankException {
		if(password.matches("[0-9]+") && password.length() == 6) {
			this.password = password;
			return;
		}
		throw new BankException("Password must consist of 6 digits");
	}
	
	public List<Order> getOrderHistory() {
		return orderHistory;
	}

	public String getHolderName() {
		return holderName;
	}
	
	public String getPassword() {
		return password;
	}

	public Integer getAccountNumber() {
		return accountNumber;
	}

	public Double getBalance() {
		return balance;
	}
	
	public void addOrder(Order order) {
		orderHistory.add(order);
	}
	
	
	public void deposit(double amount) throws BankException {
		if(amount > 0.0) {
			this.balance += amount;
			this.addOrder(new Order(this.accountNumber,OrderType.DEPOSIT,new Date(),amount));
			return;
		}
		throw new BankException("Deposit amount must be greater than $0.00");
	}
	
	public void withdraw(double amount, Bank bank) throws BankException {
		if((amount + bank.getWithdrawCharge()) <= balance && amount > 0.0) {
			balance -= (amount + bank.getWithdrawCharge());
			this.addOrder(new Order(this.accountNumber, OrderType.WITHDRAW, new Date(),(amount + bank.getWithdrawCharge())));
			return;
		}
		throw new BankException("Withdraw amount must be greater than $0.00 and the account must have enough balance");
	}
	
	@Override
	public String toString() {
		return holderName + 
				";" + accountNumber + 
				";" + password +
				";" + String.format("%.2f", balance) 
				;
	}
			
}
