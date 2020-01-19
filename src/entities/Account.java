package entities;

public class Account {
	
	private String holderName;
	private Integer accountNumber;
	protected Double balance;
	
	public Account(String holderName, Integer accountNumber, Double balance) {
		this.holderName = holderName;
		this.accountNumber = accountNumber;
		this.balance = balance;
	}

	public Account(String holderName, Integer accountNumber) {
		this.holderName = holderName;
		this.accountNumber = accountNumber;
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
