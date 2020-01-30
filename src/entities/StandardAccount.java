package entities;

import java.util.Date;

import entities.enums.OrderType;
import exceptions.BankException;

public class StandardAccount extends Account {

	private Double loanLimit;
	private Double transferLimit;

	public StandardAccount(String holderName, Integer accountNumber, String password, Double balance, Double loanLimit,
			Double transferLimit) throws BankException {
		super(holderName, accountNumber, password, balance);
		setLoanLimit(loanLimit);
		setTransferLimit(transferLimit);
	}

	public StandardAccount(String holderName, Integer accountNumber, String password, Double loanLimit,
			Double transferLimit) throws BankException {
		super(holderName, accountNumber, password);
		setLoanLimit(loanLimit);
		setTransferLimit(transferLimit);
	}

	public Double getLoanLimit() {
		return loanLimit;
	}

	public Double getTransferLimit() {
		return transferLimit;
	}

	public void setLoanLimit(Double loanLimit) throws BankException {
		if (loanLimit >= 0.0) {
			this.loanLimit = loanLimit;
			return;
		}
		throw new BankException("Loan limit must be equal or greater than $0.00");
	}

	public void setTransferLimit(Double transferLimit) throws BankException {
		if (transferLimit >= 0.0) {
			this.transferLimit = transferLimit;
			return;
		}
		throw new BankException("Transfer limit must be equal or greater than $0.00");
	}

	public void transfer(Bank bank, int accountNumber, double amount) throws BankException {
		if (amount > 0 && amount <= transferLimit) {
			if (balance >= (amount + bank.getTransferCharge())) {
				Account acc = bank.getAccounts().stream().filter(x -> x.getAccountNumber() == accountNumber).findFirst()
						.orElse(null);
				if (acc != null) {
					acc.deposit(amount);
					balance -= (amount + bank.getTransferCharge());
					this.addOrder(new TransferOrder(this.getAccountNumber(), OrderType.TRANSFER, new Date(),
							amount + bank.getTransferCharge(), amount, accountNumber));
					return;
				}
				throw new BankException("Account not found");
			}
			throw new BankException("Not enough balance");
		}
		throw new BankException("Amount must be between $0.01 and $" + String.format("%.2f", transferLimit));
	}

	public void loan(Bank bank, double amount, int numberOfInstallments) throws BankException {
		if (amount > 0 && amount <= loanLimit) {
			if (numberOfInstallments >= 1 && numberOfInstallments <= 12) {
				balance += amount;
				this.addOrder(new LoanOrder(this.getAccountNumber(), OrderType.LOAN, new Date(), amount,
						amount * (1 + bank.getLoanCharge()), numberOfInstallments, 0,
						(amount * (1 + bank.getLoanCharge())) / numberOfInstallments));
				return;
			}
			throw new BankException("Number of installments must be between 1 and 12");
		}
		throw new BankException("Loan amount must be between $0.01 and $" + String.format("%.2f", loanLimit));
	}

	public void updateLoans() { // function that updates the installments of current loans to be paid based on
								// the date, one installment each month
		for (Order order : this.getOrderHistory()) {

			if (order instanceof LoanOrder) {

				LoanOrder loanOrder = (LoanOrder) order;
				int numberOfPaidInstallments = loanOrder.getNumberOfPaidInstallments();
				int numberOfInstallments = loanOrder.getNumberOfInstallments();

				if (numberOfInstallments > numberOfPaidInstallments) {
					Date dateNow = new Date();
					Date loanDate = loanOrder.getDate();
					long differenceInMonths = (dateNow.getTime() - loanDate.getTime()) / (2592000000L);
					if (differenceInMonths > numberOfPaidInstallments) {
						int installmentsToPay = (int) (differenceInMonths - numberOfPaidInstallments);
						if (installmentsToPay > (numberOfInstallments - numberOfPaidInstallments)) {
							loanOrder.addNumberOfPaidInstallments(numberOfInstallments - numberOfPaidInstallments);
							balance -= (numberOfInstallments - numberOfPaidInstallments)
									* loanOrder.getValuePerInstallment();
						} else {
							loanOrder.addNumberOfPaidInstallments(installmentsToPay);
							balance -= installmentsToPay * loanOrder.getValuePerInstallment();
						}
					}
				}
			}
		}
	}
	
	@Override 
	public void printAccount() {
		super.printAccount();
		System.out.println("Account Type: Standard" );
	}

	@Override
	public String toString() {
		return super.toString() + ";" + "STANDARD" + ";" + String.format("%.2f", loanLimit) + ";"
				+ String.format("%.2f", transferLimit);
	}

}
