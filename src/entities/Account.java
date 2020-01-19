package entities;

import java.util.ArrayList;
import java.util.List;

public class Account {
	
	private String holderName;
	private Integer accountNumber;
	protected Double balance;
	private List<Order> orderHistory = new ArrayList<>();
	
	public Account(String holderName, Integer accountNumber, Double balance) {
		this.holderName = holderName;
		this.accountNumber = accountNumber;
		this.balance = balance;
	}

	public Account(String holderName, Integer accountNumber) {
		this.holderName = holderName;
		this.accountNumber = accountNumber;
	}
	
	public List<Order> getOrderHistory() {
		return orderHistory;
	}

	public String getHolderName() {
		return holderName;
	}

	public Integer getAccountNumber() {
		return accountNumber;
	}

	public Double getBalance() {
		return balance;
	}
	
	public boolean addOrder(Order order) {
		if(order != null) {
			orderHistory.add(order);
			return true;
		}
		return false;
	}
	
	public boolean removeAccount(Order order){
		if(order != null && orderHistory.contains(order)){
			orderHistory.remove(order);
			return true;
		}
		return false;
	}

	public boolean deposit(double amount) {
		if(amount > 0) {
			balance += amount;
			return true;
		}
		return false;
	}
	
	public boolean withdraw(double amount, Bank bank) {
		if((amount + bank.getWithdrawCharge()) <= balance) {
			balance -= (amount + bank.getWithdrawCharge());
			return true;
		}
		return false;
	}
			

}
