package entities;

import java.text.SimpleDateFormat;
import java.util.Date;

import entities.enums.OrderType;

public class Order {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	private Integer accountNumber;
	private OrderType orderType;
	private Date date;
	private Double totalValue;
		
	public Order(Integer accountNumber, OrderType orderType, Date date, Double totalValue) {
		this.accountNumber = accountNumber;
		this.orderType = orderType;
		this.date = date;
		this.totalValue = totalValue;
	}
	
	public Integer getAccountNumber() {
		return accountNumber;
	}
	public OrderType getOrderType() {
		return orderType;
	}
	public Date getDate() {
		return date;
	}
	public Double getTotalValue() {
		return totalValue;
	}

	@Override
	public String toString() {
		return "Order data:\n"
				+ "accountNumber = " + accountNumber + 
				", orderType = " + orderType + 
				"\ndate = " + sdf.format(date) + 
				", totalValue = " + String.format("%.2f",totalValue) ;
	}

	
	
	
		
}
