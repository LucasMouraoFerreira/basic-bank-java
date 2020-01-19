package entities;

import java.util.ArrayList;
import java.util.List;

public class Bank {
	
	private Double trasferCharge; // absolute value
	private Double withdrawCharge; // absolute value
	private Double loanCharge; // percentage
	private List<Account> accounts = new ArrayList<>();
	private Integer numberOfAccounts;
	private Double savingsAccountIncome; // percentage
	
	public Bank(Double trasferCharge, Double withdrawCharge, Double loanCharge, Double savingsAccountIncome) {
		this.trasferCharge = trasferCharge;
		this.withdrawCharge = withdrawCharge;
		this.loanCharge = loanCharge;
		this.savingsAccountIncome = savingsAccountIncome;
	}

	public Double getsavingsAccountIncome() {
		return savingsAccountIncome;
	}

	public void setSavingsAccountInterest(Double savingsAccountIncome) {
		if(savingsAccountIncome >= 0.0) {
			this.savingsAccountIncome = savingsAccountIncome;
		}		
	}	
	
	public Double getTrasferCharge() {
		return trasferCharge;
	}


	public void setTrasferCharge(Double trasferCharge) {
		if(trasferCharge >= 0.0) {
			this.trasferCharge = trasferCharge;
		}		
	}


	public Double getWithdrawCharge() {
		return withdrawCharge;
	}


	public void setWithdrawCharge(Double withdrawCharge) {
		if(withdrawCharge >= 0.0) {
			this.withdrawCharge = withdrawCharge;
		}		
	}


	public Double getLoanCharge() {
		return loanCharge;
	}


	public void setLoanCharge(Double loanCharge) {
		if(loanCharge >= 0.0) {
			this.loanCharge = loanCharge;
		}		
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
	
	public boolean addAccount(Account account) {
		if(account != null) {
			accounts.add(account);
			return true;
		}
		return false;
	}
	
	public boolean removeAccount(Account account) {
		if(account != null && accounts.contains(account)) {
			accounts.remove(account);
			return true;
		}
		return false;
	}

}
