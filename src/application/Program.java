package application;

import java.util.Locale;

import controller.Controller;

import entities.Bank;

public class Program {

	public static void main(String[] args){
		
		Locale.setDefault(Locale.US);
				
		Bank bank = Controller.readFromDatabase();
		
		Controller.manageApplication(bank);
		
		Controller.writeToDatabase(bank);
	
	}
}
