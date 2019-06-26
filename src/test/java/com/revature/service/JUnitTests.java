package com.revature.service;

import org.junit.Test;

import com.revature.Project0FirstAttempt.BankingDAO;
import com.revature.Project0FirstAttempt.BankingImpDAO;

public class JUnitTests {

	//when mixed with p3 or u3, this makes an invalid pair 
	private String p2 = "pass2";
	
	//valid pair
	private String u3 = "user3";
	private String p3 = "pass3";
	
	@Test
	public void testLogin() {
		
		BankingDAO bankDAO = new BankingImpDAO();
		//valid credentials:
		System.out.println("using valid credentials: " + u3  + " " + p3 + "");
		System.out.println(bankDAO.login(u3, p3));
		
		//invalid credentials:
		System.out.println("using invalid credentials: " + u3 + " " + p2);
		bankDAO.login(u3, p2);
		System.out.println("\n\n");
	}
	
	@Test
	public void testBalance() {
		BankingDAO bankDAO = new BankingImpDAO();
		
		//valid credentials:
		System.out.println("using valid credentials: " + u3  + " " + p3 + "");
		bankDAO.viewBalance(u3, p3);
		System.out.println("\n");
		//invalid credentials:
		System.out.println("using invalid credentials: " + u3 + " " + p2 + "");
		bankDAO.viewBalance(u3, p2);
	}
	
	//the other methods run an error towards the end of them... 
	//Couldn't figure out how to avoid the null pointer error
	

}
