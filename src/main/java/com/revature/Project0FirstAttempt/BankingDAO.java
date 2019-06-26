package com.revature.Project0FirstAttempt;

import java.io.IOException;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.revature.exception.ErrorMessage;

public interface BankingDAO {
	
	/*
	 * 	a.	As a user I can login and logout.
		b.	As a user I can view my balance.
		c.	As a user I can withdraw money.
		d.	As a user I can deposit money.
	 */
	String login(String username, String pass);
	void logout();
	String viewBalance(String username, String pass);
	Float withdraw(float amount, String username, String pass) throws IOException, ErrorMessage;
	Float deposit(float amount, String username, String pass) throws IOException;
	void writeToFile(Banking bank);
	void writeToFileBlank(); 
	String readFromFile(int lineNum) throws IOException;
	
}
