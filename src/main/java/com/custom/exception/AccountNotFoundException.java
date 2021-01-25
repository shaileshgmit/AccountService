package com.custom.exception;

public class AccountNotFoundException extends RuntimeException{
	
	private long id;
	
	public AccountNotFoundException(long id) {
		super("No Account exits with id: " + id);
	}

}
