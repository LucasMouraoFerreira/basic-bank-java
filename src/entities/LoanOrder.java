package entities;

import java.util.Date;

import entities.enums.OrderType;

public class LoanOrder extends Order {

	private Double loanValue; // value that will be paid
	private Integer numberOfInstallments;
	private Integer numberOfPaidInstallments;
	private Double valuePerInstallment;
	
	public LoanOrder(Integer accountNumber, OrderType orderType, Date date, Double totalValue, Double loanValue,
			Integer numberOfInstallments, Integer numberOfPaidInstallments, Double valuePerInstallment) {
		super(accountNumber, orderType, date, totalValue);
		this.loanValue = loanValue;
		this.numberOfInstallments = numberOfInstallments;
		this.numberOfPaidInstallments = numberOfPaidInstallments;
		this.valuePerInstallment = valuePerInstallment;
	}

	public Integer getNumberOfInstallments() {
		return numberOfInstallments;
	}

	public Integer getNumberOfPaidInstallments() {
		return numberOfPaidInstallments;
	}

	public Double getValuePerInstallment() {
		return valuePerInstallment;
	}
	
	public Double getLoanValue() {
		return loanValue;
	}
	
	public void addNumberOfPaidInstallments(int number) {
		this.numberOfPaidInstallments += number;
	}

	@Override
	public void printOrder() {
		super.printOrder();
		System.out.println("Loan total value: $" + String.format("%.2f", getLoanValue())
		+ " Value per installment: $" + String.format("%.2f", getValuePerInstallment())
		+"\nInstallments: " + getNumberOfPaidInstallments() + "/" + getNumberOfInstallments() + "\n");
	}
	
	@Override
	public String toString() {
		return  super.toString() +
				";" + String.format("%.2f", loanValue) + 
				";" + numberOfInstallments + 
				";" + numberOfPaidInstallments + 
				";" + String.format("%.2f", valuePerInstallment);
	}
}
