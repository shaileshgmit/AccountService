package com.account.dto;

import java.util.Date;


public class AccountDto {
	
	private Long id;
	private String accountType;
	private Date openDate;
	private String branch;
	private char minorIndicator;
	private Long customerId;
	private String customerName;
	
	public AccountDto(){
		
	}
	
	public AccountDto(Long id, String accountType, Date openDate, String branch, char minorIndicator, Long customerId,
			String customerName) {
		super();
		this.id = id;
		this.accountType = accountType;
		this.openDate = openDate;
		this.branch = branch;
		this.minorIndicator = minorIndicator;
		this.customerId = customerId;
		this.customerName = customerName;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public char getMinorIndicator() {
		return minorIndicator;
	}
	public void setMinorIndicator(char minorIndicator) {
		this.minorIndicator = minorIndicator;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	
	
}
