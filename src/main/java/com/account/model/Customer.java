package com.account.model;

import java.util.Date;

public class Customer {
	
	private Long id;
	private String userName;
	private Date dateOfBirth;
	private char gender;
	private String phoneNumber;
	private String password;
	private Role role;
	public Customer() {
		
	}
	
	public Customer(String userName, Date dateOfBirth, char gender, String phoneNumber, Role role,String password) {
		super();
		this.userName = userName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.password=password;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

	public static Customer getRequestObject(AccountUser ac) {
		Customer customer = new Customer();
		customer.setUserName(ac.getCustomer().getUserName());
		customer.setDateOfBirth(ac.getCustomer().getDateOfBirth());
		customer.setGender(ac.getCustomer().getGender());
		customer.setPhoneNumber(ac.getCustomer().getPhoneNumber());
		customer.setRole(new Role("Customer","001"));
		customer.setPassword(ac.getCustomer().getPassword());
		return customer;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

}
