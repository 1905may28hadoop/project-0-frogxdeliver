package com.revature.Project0FirstAttempt;

import java.util.Scanner;

public class Options {
	
	public Options(int userNum) {
		Scanner in = new Scanner(System.in);
		
		while (userNum!=6) {
			System.out.println("enter a value:");
			userNum = in.nextInt();
			switch(userNum) {
			case 1: 
				System.out.println("option 1");
				userNum = 0;
				break;
			case 2:
				System.out.println("option 2");
				userNum = 0;
				break;
			case 3:
				System.out.println("option 3");
				userNum = 0;
				break;
			case 4:
				System.out.println("option 4");
				userNum = 0;
				break;
			case 5:
				System.out.println("option 5");
				userNum = 0;
				break;
			default:
				System.out.println("exiting the loop");
				userNum = 6;
			}	
		}
		in.close();
	}
}
