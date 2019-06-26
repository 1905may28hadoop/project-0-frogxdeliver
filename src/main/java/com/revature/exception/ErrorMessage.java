package com.revature.exception;

import org.apache.log4j.spi.ErrorCode;

public class ErrorMessage extends Exception{
	
	public ErrorMessage () {
        super("user credentials are not invalid");
    }
	
	public ErrorMessage(float balance) {
		super("Cannot exceed the current balance: " + balance + "\n");
	}
	
}
