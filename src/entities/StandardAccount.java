package entities;

import java.util.Date;

import entities.enums.OrderType;

public class StandardAccount extends Account {
	
	private Double loanLimit;
	private Double transferLimit;
	
	public StandardAccount(String holderName, Integer accountNumber, String password, Double balance, Double loanLimit,
			Double transferLimit) {
		super(holderName, accountNumber, password, balance);
		this.loanLimit = loanLimit;
		this.transferLimit = transferLimit;
	}
	
	public StandardAccount(String holderName, Integer accountNumber, String password, Double loanLimit,
			Double transferLimit) {
		super(holderName, accountNumber, password);
		this.loanLimit = loanLimit;
		this.transferLimit = transferLimit;
	}

	public Double getLoanLimit() {
		return loanLimit;
	}

	public Double getTransferLimit() {
		return transferLimit;
	}

	public void setLoanLimit(Double loanLimit) {
		this.loanLimit = loanLimit;
	}

	public void setTransferLimit(Double transferLimit) {
		this.transferLimit = transferLimit;
	}

	public boolean BankTransfer(Bank bank, int accountNumber, double amount) {
		if(amount > 0 && amount <= transferLimit && balance >= (amount+bank.getTrasferCharge())) {
			Account acc = bank.getAccounts().stream().filter(x -> x.getAccountNumber() == accountNumber).findFirst().orElse(null);
			if(acc != null) {
				acc.deposit(amount);
				balance -= (amount + bank.getTrasferCharge());
				this.addOrder(new TransferOrder(this.getAccountNumber(), OrderType.TRANSFER, new Date(),
						amount + bank.getTrasferCharge(), amount,
						accountNumber));
				return true;
			}
			return false;
		}
		return false;
	}
	
	public boolean bankLoan(Bank bank ,double amount, int numberOfInstallments) {
		if(amount > 0 && amount <= loanLimit) {
			balance += amount;
			this.addOrder(new LoanOrder(this.getAccountNumber(), OrderType.LOAN, new Date(), amount, amount*(1+bank.getLoanCharge()),
				numberOfInstallments, 0, (amount*(1+bank.getLoanCharge()))/numberOfInstallments));
		}
		return false;
	}
	
	public void updateLoans() { // function that updates the installments of current loans to be paid based on the date, one installment each month
		for(Order order : this.getOrderHistory()) {
			
			if(order instanceof LoanOrder) {
				
				LoanOrder loanOrder = (LoanOrder)order;
				int numberOfPaidInstallments = loanOrder.getNumberOfPaidInstallments();
				int numberOfInstallments = loanOrder.getNumberOfInstallments();
				
				if(numberOfInstallments > numberOfPaidInstallments)	{
					Date dateNow = new Date();
					Date loanDate = loanOrder.getDate();
					long differenceInMonths = (dateNow.getTime() - loanDate.getTime())/(2592000000L);
					if(differenceInMonths > numberOfPaidInstallments) {
						int installmentsToPay = (int)(differenceInMonths - numberOfPaidInstallments);
						if(installmentsToPay > (numberOfInstallments-numberOfPaidInstallments)) {
							loanOrder.addNumberOfPaidInstallments(numberOfInstallments-numberOfPaidInstallments);
							balance -= (numberOfInstallments-numberOfPaidInstallments)*loanOrder.getValuePerInstallment();
						}else {
							loanOrder.addNumberOfPaidInstallments(installmentsToPay);
							balance -= installmentsToPay*loanOrder.getValuePerInstallment();
						}
					}
				}				
			}
		}
	}

	
	@Override
	public String toString() {
		return super.toString() + 
				";" +"STANDARD" + 
				";" + String.format("%.2f", loanLimit)  + 
				";" + String.format("%.2f", transferLimit);
	}
	
	
}
