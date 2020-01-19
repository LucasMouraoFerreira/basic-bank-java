package entities;

import java.util.Date;

public class SavingsAccount extends Account{

	private Date creationDate;
	private Integer numberOfMonthsActive;
	
	public SavingsAccount(String holderName, Integer accountNumber, String password, Date creationDate, Integer numberOfMonthsActive) {
		super(holderName, accountNumber, password);
		this.creationDate = creationDate;
		this.numberOfMonthsActive = numberOfMonthsActive;
	}
	
	public SavingsAccount(String holderName, Integer accountNumber, String password, Double balance, Date creationDate, Integer numberOfMonthsActive) {
		super(holderName, accountNumber, password, balance);
		this.creationDate = creationDate;
		this.numberOfMonthsActive = numberOfMonthsActive;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public Integer getNumberOfMonthsActive() {
		return numberOfMonthsActive;
	}

	public void AddMonths(int quantity) {
		numberOfMonthsActive += quantity;
	}
	
	public void updateIncome(Bank bank) { // function that updates the income each month
		Date dateNow = new Date();
		long differenceInMonths = (dateNow.getTime() - creationDate.getTime())/(1000*3600*24*30);
		if(differenceInMonths > numberOfMonthsActive) {
			int monthsToAdd = (int)(differenceInMonths - numberOfMonthsActive);
			this.AddMonths(monthsToAdd);
			balance = balance*(Math.pow((1+bank.getsavingsAccountIncome()),monthsToAdd));
		}
	}	
}
