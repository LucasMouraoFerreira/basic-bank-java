package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.enums.OrderType;

public class Account {
	
	
	private String holderName;
	private Integer accountNumber;
	protected Double balance;
	private List<Order> orderHistory = new ArrayList<>();
	private String password;
	
	public Account(String holderName, Integer accountNumber, String password ,Double balance) {
		this.holderName = holderName;
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.password = password;
	}

	public Account(String holderName, Integer accountNumber, String password) {
		this.holderName = holderName;
		this.accountNumber = accountNumber;
		this.password = password;
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
	
	
	public boolean deposit(double amount) {
		if(amount > 0) {
			balance += amount;
			this.addOrder(new Order(this.accountNumber,OrderType.DEPOSIT,new Date(),amount));
			return true;
		}
		return false;
	}
	
	public boolean withdraw(double amount, Bank bank) {
		if((amount + bank.getWithdrawCharge()) <= balance && amount > 0) {
			balance -= (amount + bank.getWithdrawCharge());
			this.addOrder(new Order(this.accountNumber, OrderType.WITHDRAW, new Date(),(amount + bank.getWithdrawCharge())));
			return true;
		}
		return false;
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
