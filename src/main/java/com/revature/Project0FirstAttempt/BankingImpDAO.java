package com.revature.Project0FirstAttempt;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.exception.ErrorMessage;

public class BankingImpDAO implements BankingDAO{

	public Logger logger = Logger.getLogger(Banking.class);

	@Override
	//checks if the user has valid credentials, and set's the variables necessary to retrieve that user's data
	public String login(String username, String pass) {
		String valid = "user credentials are valid\n";
		boolean validB = true;  	//boolean used for validated the user. Defaults as true unless an exception is caught
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Banking bank = null;
		
		//connection we used in class, but instead of using a single column, I used 2 columns as composite keys to get the 
		//values within a user's row.
		//ALso writes it to the file: "userInfo" before closing the connection
		try(Connection conn = ConnectionUtil.getConnection()){
			statement = conn.prepareStatement("SELECT * FROM testBank WHERE Login_Username = ? AND Login_Password = ?");
			statement.setString(1, username);
			statement.setString(2, pass);
			statement.execute();
			resultSet = statement.getResultSet();
			resultSet.next();
			bank = new Banking(resultSet);
			
			conn.close();
		}catch(SQLException e) {
			validB = false;
//			e.printStackTrace();
			logger.error(e);
		}finally {
			//will only write the changes if the user is validated
			if(validB) {
				writeToFile(bank);
			}
			
			CloseStreams.close(statement);
			CloseStreams.close(resultSet);
		}
		
		//used to determine that the user's input was not valid
		if (!validB) {
			try {
				//displays: user credentials are not invalid
				throw new ErrorMessage();
			} catch (ErrorMessage e1) {
				System.out.println(e1.getMessage());
			}
		}
		
		
		return valid;
	}
	
	@Override
	//uses logic to find the user's new total, based on the previous amount
	//also includes the previous connection
	
	//currently doesn't update the database... Null pointer exception error is preventing it from working
	public Float deposit(float amount, String username, String pass) throws IOException {
		// TODO Auto-generated method stub
		//add the amount into the user's current balance
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
//		Banking bank = null;
		
//		//variables used for logical operations
		float newTotal;
		float oldTotal;
		//when subtracting the previous total by the desired amount from the user
		//in order to get the new total
		//Also calls readFromFile, to get the user's current balance
		oldTotal = Float.parseFloat(readFromFile(3));
		newTotal = oldTotal + amount;
		
		//this time the query updates the old data with the newTotal
		//again uses the composite keys login_username & login_password
		//and writes it to the file: "userInfo" before closing the connection (however because of the current errors, the writing doesn't occur)
		try(Connection conn = ConnectionUtil.getConnection()){
			statement = conn.prepareStatement("UPDATE testBank SET Current_Balance = ? WHERE Login_Username = ? AND Login_Password = ?");
			statement.setFloat(1, newTotal);
			statement.setString(2, username);
			statement.setString(3, pass);
			statement.execute();
			resultSet = statement.getResultSet();
			
//			resultSet.moveToCurrentRow();
//			resultSet.next();
//			bank = new Banking(resultSet);
//			writeToFile(bank);
			conn.close();
			//commented exceptions out, so that nothing will show during presentations
			//however, the code will break at the 
			//resultSet.next();
			//4 lines above the first comment
		} catch (SQLException e) {
//			e.printStackTrace();
			logger.error(e);
		}catch(NullPointerException e){
//			e.printStackTrace();
			logger.error(e);
		}finally {
			CloseStreams.close(statement);
			CloseStreams.close(resultSet);
		}
		System.out.println("New total: ");
		return newTotal;
	}

	

	@Override
	public String viewBalance(String username, String pass) {
		//show the current user's balance
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Banking bank = null;
		
		String valid = "\n";
	
		try(Connection conn = ConnectionUtil.getConnection()){
			statement = conn.prepareStatement("SELECT * FROM testBank WHERE Login_Username = ? AND Login_Password = ?");
			statement.setString(1, username);
			statement.setString(2, pass);
			statement.execute();
			resultSet = statement.getResultSet();
			resultSet.next();
			bank = new Banking(resultSet);

			//prints the user's current balance
			System.out.println("Your current balance is: "+resultSet.getFloat(4));
			//Important ^^^^
			conn.close();
		} catch (SQLException e) {
			//in the case a user entered incorrect data
			System.out.println("you need to login first");
//			e.printStackTrace();
		}finally {
			CloseStreams.close(statement);
			CloseStreams.close(resultSet);
			
		}
		
		return valid;
	}
	

	@Override
	//currently doesn't update the database... Null pointer exception error is preventing it from working
	public Float withdraw(float amount, String username, String pass) throws IOException, ErrorMessage {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
//		Banking bank = null;
		
		//variables used for logical operations
		float newTotal;
		float oldTotal;
		//when subtracting the previous total by the desired amount from the user
		//in order to get the new total
		//Also calls readFromFile, to get the user's current balance
		oldTotal = Float.parseFloat(readFromFile(3));
		newTotal = oldTotal - amount;
		
		if (amount > oldTotal) {
			throw new ErrorMessage(oldTotal);
		}
		
		
		//updates the DB with the current info, and writes it to the file: "userInfo" before closing the connection
		//(however because of the current errors, the writing doesn't occur)
		try(Connection conn = ConnectionUtil.getConnection()){
			statement = conn.prepareStatement("UPDATE testBank SET Current_Balance = ? WHERE Login_Username = ? AND Login_Password = ?");
			statement.setFloat(1, newTotal);
			statement.setString(2, username);
			statement.setString(3, pass);
			statement.execute();
//			resultSet = statement.getResultSet();
//			resultSet.next();
//			bank = new Banking(resultSet);
//			writeToFile(bank);
			conn.close();
			//commented exceptions out, so that nothing will show during presentations
			//however, the code will break at the 
			//resultSet.next();
			//4 lines above the first comment
		} catch (SQLException e) {
//			e.printStackTrace();
		}catch(NullPointerException e){
//			e.printStackTrace();
		}finally {
			CloseStreams.close(statement);
			CloseStreams.close(resultSet);
			
		}	
		
		System.out.print("\n\nNew Total: ");
		
		return newTotal;
	}

	

	@Override
	public void writeToFile(Banking bank) {
		FileWriter fileWriter = null;
		try {
			//file location
			fileWriter =  new FileWriter("src/test/resources/userInfo.txt");
			
			//writes each value in the value bank to the file
			fileWriter.write(bank.getAcctNum() + "\n");
			fileWriter.write(bank.getLogin_username() + "\n");
			fileWriter.write(bank.getLogin_password() + "\n");
			fileWriter.write(bank.getCurrent_balance() + "\n");
			fileWriter.close();
		}  catch(FileNotFoundException e) {
//			e.printStackTrace();
			logger.error(e);
		}catch(IOException e) {
//			e.printStackTrace();
			logger.error(e);
		}
		
	}

	@Override
	public String readFromFile(int lineNum) throws IOException {
		String line;
		Scanner scanner = new Scanner("src/test/resources/userInfo.txt");  
			//TODO 
			//(should just be a for loop through the file, but this get's the job done)
			//https://stackoverflow.com/questions/2312756/how-to-read-a-specific-line-using-the-specific-line-number-from-a-file-in-java
			//3 for balance
			line = Files.readAllLines(Paths.get("src/test/resources/userInfo.txt")).get(lineNum);
//			System.out.println(line);

		return line;
	}


	@Override
	public void writeToFileBlank() {
		// TODO Auto-generated method stub
		Logger log = null;
		FileWriter fileWriter = null;
		try {
//			System.out.println("writing to file");
			fileWriter =  new FileWriter("src/test/resources/userInfo.txt");
			
			//writes each value in the value bank to the file
			fileWriter.write(" ");
			fileWriter.close();
		}  catch(FileNotFoundException e) {
//			e.printStackTrace();
			logger.error(e);
		}catch(IOException e) {
//			e.printStackTrace();
			logger.error(e);
		}
	}

	@Override
	public void logout() {
		//simply call a statement that will then close the program (closing the program happens outside of this scope)
		writeToFileBlank();
	}



}
