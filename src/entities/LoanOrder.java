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
	
	public void addNumberOfPaidInstallments() {
		this.numberOfPaidInstallments++;
	}

	@Override
	public String toString() {
		return  super.toString() +"\n" + "TransferOrder [loanValue=" + loanValue + ", numberOfInstallments=" + numberOfInstallments
				+ ", numberOfPaidInstallments=" + numberOfPaidInstallments + ", valuePerInstallment="
				+ valuePerInstallment +"]";
	}
}
