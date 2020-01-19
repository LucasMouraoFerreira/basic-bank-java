package entities;

import java.util.Date;

import entities.enums.OrderType;

public class TransferOrder extends Order {
	
	private Double valueTransferred;
	private Integer accountToBePaid;
	
	public TransferOrder(Integer accountNumber, OrderType orderType, Date date, Double totalValue, Double valueTransferred,
			Integer accountToBePaid) {
		super(accountNumber, orderType, date, totalValue);
		this.valueTransferred = valueTransferred;
		this.accountToBePaid = accountToBePaid;
	}

	public Double getValueTransferred() {
		return valueTransferred;
	}

	public Integer getAccountToBePaid() {
		return accountToBePaid;
	}

	@Override
	public String toString() {
		return "TransferOrder [valueTransferred=" + valueTransferred + ", accountToBePaid=" + accountToBePaid
				+ ", toString()=" + super.toString() + "]";
	}		
	
	
}
