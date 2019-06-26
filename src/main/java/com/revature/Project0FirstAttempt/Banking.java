package com.revature.Project0FirstAttempt;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Banking {
	/*
	 * values:
	 * 	Account_Number number primary key,
    	Login_Username varchar(100),
    	Login_Password varchar(100),
    	Current_Balance float
	 */
	
	private int acctNum;
	private String login_username;
	private String login_password;
	private float current_balance;
	
	public Banking() {
		super();
	}
	
	//once a user logs in for the first time
	public Banking(String login_username, String login_password) {
		super();
		this.login_username = login_username;
		this.login_password = login_password;
	}
	
	public Banking(float current_balance) {
		super();
		this.current_balance = current_balance;
	}
	//for user level access
	public Banking(int acctNum, String login_username, String login_password, float current_balance) {
		super();
		this.acctNum = acctNum;
		this.login_username = login_username;
		this.login_password = login_password;
		this.current_balance = current_balance;
	}
	
	
	//used to make the result set
	public Banking(ResultSet resultSet) throws SQLException {
		this(resultSet.getInt("Account_Number"),
				resultSet.getString("Login_Username"),
				resultSet.getString("Login_Password"),
				resultSet.getFloat("Current_Balance")
				);
	}
	
	
	public int getAcctNum() {
		return acctNum;
	}
	public void setAcctNum(int acctNum) {
		this.acctNum = acctNum;
	}
	public String getLogin_username() {
		return login_username;
	}
	public void setLogin_username(String login_username) {
		this.login_username = login_username;
	}
	public String getLogin_password() {
		return login_password;
	}
	public void setLogin_password(String login_password) {
		this.login_password = login_password;
	}
	public float getCurrent_balance() {
		return current_balance;
	}
	public void setCurrent_balance(float current_balance) {
		this.current_balance = current_balance;
	}


	@Override
	public String toString() {
		return "Banking [acctNum=" + acctNum + ", login_username=" + login_username + ", login_password="
				+ login_password + ", current_balance=" + current_balance + "]";
	}
	
	
	
}
