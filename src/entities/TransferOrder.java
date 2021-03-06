package entities;

import java.util.Date;

import entities.enums.OrderType;

public class TransferOrder extends Order {
	
	private Double valueTransferred;
	private Integer accountToGetPaid;
	
	public TransferOrder(Integer accountNumber, OrderType orderType, Date date, Double totalValue, Double valueTransferred,
			Integer accountToGetPaid) {
		super(accountNumber, orderType, date, totalValue);
		this.valueTransferred = valueTransferred;
		this.accountToGetPaid = accountToGetPaid;
	}

	public Double getValueTransferred() {
		return valueTransferred;
	}

	public Integer getAccountToGetPaid() {
		return accountToGetPaid;
	}

	@Override
	public void printOrder() {
		super.printOrder();
		System.out.println("Total transferred: $" + String.format("%.2f", getValueTransferred())
		+ " Account that received: " + getAccountToGetPaid() + "\n");
	}
	
	@Override
	public String toString() {
		return  super.toString() + 
				";" + String.format("%.2f", valueTransferred) + 
				";" + accountToGetPaid;
	}		
}
