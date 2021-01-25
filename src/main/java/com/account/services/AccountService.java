package com.account.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.account.model.Account;
import com.account.model.AccountUser;
import com.account.model.Customer;
import com.account.repository.AccountRepository;
import com.custom.exception.AccountNotFoundException;


@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	/*@Autowired
	private WebClient.Builder webClientBuilder;*/
	
	@Autowired
	private RestTemplate restTemplate;
	
	//private static final String DATE_FORMATTER = "yyyy-MM-dd";
	
	private static final String GET_CUSTOMERS_ENDPOINT_URL = "http://customer-service/user/";
    private static final String GET_CUSTOMER_ENDPOINT_URL = "http://customer-service/user/";
    private static final String CREATE_CUSTOMER_ENDPOINT_URL = "http://customer-service/useradd";
    private static final String UPDATE_CUSTOMER_ENDPOINT_URL = "http://customer-service/user/{id}";
    private static final String DELETE_CUSTOMER_ENDPOINT_URL = "http://customer-service/user/{id}";
	

	public List<Account> getAllAccount() {
		return accountRepository.findAll();
	}

	public AccountUser getAccount(long id,HttpHeaders headers) {
		
		Optional<Account> account = accountRepository.findById(id);
		if (!account.isPresent())
			throw new AccountNotFoundException(id);
		HttpEntity entity = new HttpEntity(headers);
//		Customer customer = restTemplate.getForObject(GET_CUSTOMER_ENDPOINT_URL + account.get().getCustomerId(), Customer.class);
		ResponseEntity<Customer> customer = restTemplate.exchange(GET_CUSTOMER_ENDPOINT_URL + account.get().getCustomerId(), HttpMethod.GET, entity, Customer.class);
		AccountUser response = AccountUser.generateResponse(account.get(),customer.getBody());
		return response;
	}

	public void saveAccount(AccountUser account, HttpHeaders headers) {
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		Customer customer = Customer.getRequestObject(account);
		HttpEntity<Customer> request = new HttpEntity<>(customer, headers);
		ResponseEntity<Customer> customerresponse = restTemplate.postForEntity(CREATE_CUSTOMER_ENDPOINT_URL, request, Customer.class);
		System.out.println(customerresponse.getStatusCode());
		if (customerresponse.getStatusCode() == HttpStatus.OK) {
		    System.out.println("Account Created");
		    System.out.println(customerresponse.getBody());
		} 
		else {
		    System.out.println("Account creation Failed");
		    System.out.println(customerresponse.getStatusCode());
		}
	
		char isMinor = isMinor(account.getCustomer().getDateOfBirth());
		
		Account newAccount = new Account();
		newAccount.setBranch(account.getBranch());
		newAccount.setAccountType(account.getAccountType());
		newAccount.setCustomerId(customerresponse.getBody().getId());
		newAccount.setCustomerName(customerresponse.getBody().getUserName());
		newAccount.setOpenDate(account.getOpenDate());
		newAccount.setMinorIndicator(isMinor);
		System.out.println(customerresponse.getBody().getId());
		accountRepository.save(newAccount);
	}

	public Account updateAccount(Account account, long id) {
		Optional<Account> accountOptional = accountRepository.findById(id);

		if (!accountOptional.isPresent()) {
			throw new AccountNotFoundException(id);
		}
		account.setId(id);
		account.setCustomerId(accountOptional.get().getCustomerId());
		Account updatedAccount  = accountRepository.save(account);
		return updatedAccount;
	}

	public String deleteAccount(long id, HttpHeaders headers) {
		Optional<Account> account = accountRepository.findById(id);
		if (!account.isPresent())
			throw new AccountNotFoundException(id);
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);
//        ResponseEntity<String> response = restTemplate.delete("http://customer-service/user/" + account.get().getCustomerId());
		ResponseEntity<Void> responseEntity = restTemplate.exchange(DELETE_CUSTOMER_ENDPOINT_URL , HttpMethod.DELETE, httpEntity, Void.class,account.get().getCustomerId());
		accountRepository.deleteById(id);
		return "Account deleted successfully..";
		
	}
	
	protected char isMinor(Date dateOfBirth) {
		LocalDateTime now = LocalDateTime.now(); 
		LocalDateTime dob = dateOfBirth.toInstant().atZone(ZoneId.systemDefault())
							.toLocalDateTime();
		if(now.getYear()-dob.getYear() > 18) {
			return 'F';
		}
		return 'T';
	}

}
