package application;

import java.util.Date;
import java.util.List;

import entities.Account;
import entities.LoanOrder;
import entities.Order;
import entities.enums.OrderType;

public class Program {

	public static void main(String[] args) {
		
		
		Account acc = new Account("Lucas Mourão", 1000, 5000.0);
		Order order1 = new Order(1000, OrderType.DEPOSIT , new Date(), 100.0);
		LoanOrder order2 = new LoanOrder(1000, OrderType.LOAN, new Date(), 300.0, 360.0,
			3, 0, 120.0);
		
		acc.addOrder(order1);
		acc.addOrder(order2);
		
		List<Order> orders = acc.getOrderHistory();
		
		for(Order a : orders) {
			System.out.println(a);
		}

	}

}
