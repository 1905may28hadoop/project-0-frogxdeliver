package com.revature.Project0FirstAttempt;

import java.io.IOException;
import java.util.Scanner;


import com.revature.exception.ErrorMessage;

public class Console {
	public static void main(String[] args) {
		//use a switch statement to catch each option
		
 
		Scanner in = new Scanner(System.in);
		int userNum = 0;				//used for the user input
		String userName; 				//storing the username & pass for accessing the database
		String userPass;
		float withdrawAmount;
		float depositAmount;
		
		
		
		
		//TODO: add exception handling for if the user enters an invalid character (like a string)
		//currently, the program will crash if the user enters a string not an int
		
		
		//TODO: add the logOutError function to each of the options 2-4

		
		
		BankingDAO bankDAO = new BankingImpDAO();
		Banking bank = new Banking();
		
		//clears the file before a new user logs in. Prevents a new user from 
		//seeing an old user's data
		bankDAO.writeToFileBlank();
		
		
		//keep getting user input, until option 7 is chosen. Then close the console the inputStream
		while (userNum!=7) {
			System.out.println("Login   		1");
			System.out.println("Deposit 		2");
			System.out.println("View Balance 		3");
			System.out.println("Withdraw 		4");
			System.out.println("Logout			5");
			System.out.println("Exit                    6");
			System.out.print("\nChoose an option 1-6: ");
			
			userNum = in.nextInt();		
			//TODO
			//figure out why this doesn't work ;-;
			//in case the user enters a invalid character
//			try {
//				userNum = in.nextInt();				
//			}catch(Exception e){
//				System.out.println("Invalid input");
//				userNum = 0;
//			}
			
			
			
			switch(userNum) {
			
			

			case 1:
				//TODO
				//UNCOMMENT AFTER TESTING
				//get's the userName and Password from the user, and runs the login method to check if the inputed values
				//are within the current DB
//				System.out.print("\nEnter your username: ");
//				userName = in.next();
//				System.out.print("Enter your password: ");
//				userPass = in.next();
				
				
				//TODO
				//Error code2 here
//				if (userName.equals("")) {
//					try {
//						throw new ErrorMessage("Invalid characters", ErrorCode.GENERIC_FAILURE);
//					} catch (ErrorMessage e) {
//						// TODO Auto-generated catch block
////						e.printStackTrace();
//					} 
//				}
				
				//temp variables for hardcoding
				userName = "user1";
				userPass = "pass1";
				//set the values to the private members
				bank.setLogin_username(userName);
				bank.setLogin_password(userPass);
				
				//prints statements, then writes the user info to a file
				System.out.println(bankDAO.login(userName, userPass));
				userNum = 0;
				break;
				
			//DEPOSIT
			case 2:
				//messy, but adds the amount the user inputs to the current balance on the DB
				System.out.println("Enter an amount to deposit: ");
				depositAmount = in.nextFloat();
				try {
					System.out.println(bankDAO.deposit(depositAmount, bank.getLogin_username(), bank.getLogin_password()));
					//writes the change to the file. 
					//I kept getting errors referencing null pointers, so this is the makeshift version that works
					bankDAO.login(bank.getLogin_username(), bank.getLogin_password());
				} catch (IOException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				userNum = 0;
				break;
				
			
			//VIEW BALANCE
			//reads from the file "userInfo" to get the user's current balance
			case 3:
//				System.out.println(bankDAO.viewBalance(bank.getLogin_username(), bank.getLogin_password()));
				System.out.println("\n\n");
				try {
					System.out.println(bankDAO.readFromFile(3));
				} catch (IOException e2) {
//					e2.printStackTrace();
				} catch(IndexOutOfBoundsException e) {
					//caught in case the file is empty
					System.out.println("You must login first");
				}
				
				System.out.println("Press the Enter key to continue...");
				try
		        {
		            System.in.read();
		        }catch(Exception e){}
				System.out.println("\n\n\n");
				userNum = 0;
				break;
				
			//WITHDRAW
			//subtracts the user's current balance on the DB by the amount the user enters
			case 4:
//				System.out.println("\nWithdraw");
				try {
					System.out.println(bankDAO.readFromFile(3));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.out.println("Enter an amount to withdraw: ");
				withdrawAmount = in.nextFloat();
				
				try {
					System.out.println(bankDAO.withdraw(withdrawAmount, bank.getLogin_username(), bank.getLogin_password()));
					//writes the change to the file. 
					//I kept getting errors referencing null pointers, so this is the makeshift version that works
					bankDAO.login(bank.getLogin_username(), bank.getLogin_password());
				} catch (ErrorMessage e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					System.out.println(e);
				}catch (IOException e1) {
					e1.printStackTrace();
				} 
				
//				bankDAO.writeToFile(bankDAO.login(bank.getLogin_username(), bank.getLogin_password()));
				userNum = 0;
				break;
				
			//LOGOUT
			case 5:
				//Exits the loop
				System.out.println("\nUser logged out");
				
				//wipe data from file, to login another user
				bankDAO.logout();
				userNum = 6;
				break;
				
			
			//EXIT
			case 6:
				//Exits the loop
				System.out.println("\nHave a good day!");
				userNum = 7;
				break;
				
			//DEFAULT ERROR
			default:
				System.out.println("\nPlease enter an integer between 1-6");
				System.out.println("Press the Enter key to continue...");
		        //used to read the enter key press. Requires a try catch to be used
				try
		        {
		            System.in.read();
		        }catch(Exception e){} 
		        //space
		        System.out.println("\n\n\n");
				userNum = 0;
			}	
		}
		in.close(); //closes the scanner
		
		
		
	}
}
