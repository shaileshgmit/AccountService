package com.account.model;

import java.util.Date;

public class AccountUser {

	private String accountType;
	private Date openDate;
	private String branch;
	private Customer customer;
	
	public AccountUser() {
		
	}
	
	public AccountUser(String accountType, Date openDate, String branch, Customer customer) {
		super();
		this.accountType = accountType;
		this.openDate = openDate;
		this.branch = branch;
		this.customer = customer;
	}

	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public static AccountUser generateResponse(Account account, Customer customer) {
		AccountUser au= new AccountUser();
		au.setCustomer(customer);
		au.setAccountType(account.getAccountType());
		au.setBranch(account.getBranch());
		au.setOpenDate(account.getOpenDate());
		return au;
	}
	
	
	
}
