package entities;

import java.util.ArrayList;
import java.util.List;

import exceptions.BankException;

public class Bank {

	private Double transferCharge; // absolute value
	private Double withdrawCharge; // absolute value
	private Double loanCharge; // percentage
	private List<Account> accounts = new ArrayList<>();
	private Integer numberOfAccounts;
	private Double savingsAccountIncome; // percentage

	public Bank(Double trasferCharge, Double withdrawCharge, Double loanCharge, Double savingsAccountIncome){
		this.transferCharge =trasferCharge;
		this.withdrawCharge = withdrawCharge;
		this.loanCharge = loanCharge;
		this.savingsAccountIncome = savingsAccountIncome;
		numberOfAccounts = 0;
	}

	public Double getsavingsAccountIncome() {
		return savingsAccountIncome;
	}

	public void setSavingsAccountIncome(Double savingsAccountIncome) throws BankException {
		if (savingsAccountIncome >= 0.0 && savingsAccountIncome <= 1.0) {
			this.savingsAccountIncome = savingsAccountIncome;
			return;
		}
		throw new BankException("Value must be between 0.0 and 1.0");
	}

	public Double getTransferCharge() {
		return transferCharge;
	}

	public void setTransferCharge(Double transferCharge) throws BankException {
		if (transferCharge >= 0.0) {
			this.transferCharge = transferCharge;
			return;
		}
		throw new BankException("Value must be equal or greater than 0.0");
	}

	public Double getWithdrawCharge() {
		return withdrawCharge;
	}

	public void setWithdrawCharge(Double withdrawCharge) throws BankException {
		if (withdrawCharge >= 0.0) {
			this.withdrawCharge = withdrawCharge;
			return;
		}
		throw new BankException("Value must be equal or greater than 0.0");
	}

	public Double getLoanCharge() {
		return loanCharge;
	}

	public void setLoanCharge(Double loanCharge) throws BankException {
		if (loanCharge >= 0.0 && loanCharge <= 1.0) {
			this.loanCharge = loanCharge;
			return;
		}
		throw new BankException("Value must be between 0.0 and 1.0");
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public Integer getNumberOfAccounts() {
		return numberOfAccounts;
	}

	public void setNumberOfAccounts() {
		this.numberOfAccounts = accounts.size();
	}

	public void addAccount(Account account) throws BankException{
		if (account != null) {
			accounts.add(account);
			numberOfAccounts++;			
			return;
		}
		throw new BankException("You can't add a null account to the account list!");
	}

	public void removeAccount(Account account) throws BankException{
		if (account != null && accounts.contains(account)) {
			accounts.remove(account);
			return;
		}
		throw new BankException("You can't remove a null account from the account list!");
	}

	@Override
	public String toString() {
		return transferCharge + 
				";" + withdrawCharge + 
				";" + loanCharge + 
				";" + savingsAccountIncome;
	}

}
