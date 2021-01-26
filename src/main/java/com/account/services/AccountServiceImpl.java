package com.account.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.account.dto.AccountDto;
import com.account.model.Account;
import com.account.model.AccountUser;
import com.account.model.Customer;
import com.account.repository.AccountRepository;
import com.custom.exception.AccountNotFoundException;
import com.custom.exception.ExceptionResponse;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ModelMapper mapper;

	/*
	 * @Autowired private WebClient.Builder webClientBuilder;
	 */

	@Autowired
	private RestTemplate restTemplate;

	// private static final String DATE_FORMATTER = "yyyy-MM-dd";

	private static final String GET_CUSTOMERS_ENDPOINT_URL = "http://customer-service/user/";
	private static final String GET_CUSTOMER_ENDPOINT_URL = "http://customer-service/user/";
	private static final String CREATE_CUSTOMER_ENDPOINT_URL = "http://customer-service/useradd";
	private static final String UPDATE_CUSTOMER_ENDPOINT_URL = "http://customer-service/user/{id}";
	private static final String DELETE_CUSTOMER_ENDPOINT_URL = "http://customer-service/user/{id}";

	@Override
	public ResponseEntity<List<AccountDto>> getAllAccount() {

		List<Account> accounts = null;
		List<AccountDto> accountList = null;
		try {
			accounts = accountRepository.findAll();

			accountList = accounts.stream().map(account -> mapper.map(account, AccountDto.class))
					.collect(Collectors.toList());
			if (accountList.size() == 0) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			return new ResponseEntity<List<AccountDto>>(accountList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@Override
	public ResponseEntity<AccountUser> getAccount(long id, HttpHeaders headers) {
		Optional<Account> account = null;
		HttpEntity<?> entity = null;
		AccountUser useraccount = null;
		ResponseEntity<Customer> customer=null;
		try {
			account = accountRepository.findById(id);
			if (!account.isPresent())
				throw new AccountNotFoundException(id);
			 entity = new HttpEntity<>(headers);
			customer = restTemplate.exchange(
					GET_CUSTOMER_ENDPOINT_URL + account.get().getCustomerId(), HttpMethod.GET, entity, Customer.class);
			useraccount = AccountUser.generateResponse(account.get(), customer.getBody());
			return ResponseEntity.of(Optional.of(useraccount));
		} 
		catch (AccountNotFoundException ex) {
			ExceptionResponse response = getExceptionResponse(ex);
			return new ResponseEntity(response, HttpStatus.NOT_FOUND);

		} 
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Override
	public void saveAccount(AccountUser account, HttpHeaders headers) {

		Customer customer = null;
		HttpEntity<Customer> request = null;
		ResponseEntity<Customer> customerresponse = null;
		char isMinor;
		Account newAccount=null;

		try {
			headers.setContentType(MediaType.APPLICATION_JSON);
			customer = Customer.getRequestObject(account);
			request = new HttpEntity<>(customer, headers);
			customerresponse = restTemplate.postForEntity(CREATE_CUSTOMER_ENDPOINT_URL, request, Customer.class);
			if (customerresponse.getStatusCode() == HttpStatus.OK) {
				System.out.println("Account Created");
				System.out.println(customerresponse.getBody());
			} else {
				System.out.println("Account creation Failed");
				System.out.println(customerresponse.getStatusCode());
			}

			isMinor = isMinor(account.getCustomer().getDateOfBirth());

			newAccount = new Account();
			newAccount.setBranch(account.getBranch());
			newAccount.setAccountType(account.getAccountType());
			newAccount.setCustomerId(customerresponse.getBody().getId());
			newAccount.setCustomerName(customerresponse.getBody().getUserName());
			newAccount.setOpenDate(account.getOpenDate());
			newAccount.setMinorIndicator(isMinor);
			accountRepository.save(newAccount);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public ResponseEntity<AccountDto> saveCustomerAccount(AccountDto accountDto, HttpHeaders headers) {
		
		Account newAccount=null;
		try {
			newAccount = new Account();
			newAccount.setBranch(accountDto.getBranch());
			newAccount.setAccountType(accountDto.getAccountType());
			newAccount.setCustomerId(accountDto.getCustomerId());
			newAccount.setCustomerName(accountDto.getCustomerName());
			newAccount.setOpenDate(accountDto.getOpenDate());
			newAccount.setMinorIndicator(accountDto.getMinorIndicator());
			return new ResponseEntity<AccountDto>(mapper.map(accountRepository.saveAndFlush(newAccount), AccountDto.class),HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		

	}

	@Override
	public ResponseEntity<AccountDto> updateAccount(AccountDto accountDto, long id) {
		
		Optional<Account> accountOptional=null;
		Account account=null;
		try {
			accountOptional = accountRepository.findById(id);

			if (!accountOptional.isPresent()) {
				throw new AccountNotFoundException(id);
			}
			account = accountOptional.get();
			account.setId(id);
			account.setCustomerId(accountOptional.get().getCustomerId());
			account.setCustomerName(accountDto.getCustomerName());
			account.setBranch(accountDto.getBranch());
			account.setAccountType(accountDto.getAccountType());
			return new ResponseEntity<AccountDto>(mapper.map(accountRepository.save(account), AccountDto.class),
					HttpStatus.OK);
		}
		catch (AccountNotFoundException ex) {
			ExceptionResponse response = getExceptionResponse(ex);
			return new ResponseEntity(response, HttpStatus.NOT_FOUND);

		} 
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}

	@Override
	public ResponseEntity<Void> deleteAccount(long id, HttpHeaders headers) {
		Optional<Account> account=null;
		HttpEntity<?> httpEntity =null;
		ResponseEntity<Void> responseEntity;
		try {
			account = accountRepository.findById(id);
			if (!account.isPresent())
				throw new AccountNotFoundException(id);

			headers.setContentType(MediaType.APPLICATION_JSON);
			httpEntity = new HttpEntity<>(headers);
			responseEntity = restTemplate.exchange(DELETE_CUSTOMER_ENDPOINT_URL, HttpMethod.DELETE,
					httpEntity, Void.class, account.get().getCustomerId());
			accountRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).build();
			
		}
		catch (AccountNotFoundException ex) {
			ExceptionResponse response = getExceptionResponse(ex);
			return new ResponseEntity(response, HttpStatus.NOT_FOUND);

		} 
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}

	@Override
	public char isMinor(Date dateOfBirth) {
		try {
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime dob = dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			if (now.getYear() - dob.getYear() > 18) {
				return 'F';
			}
			return 'T';
		}catch(Exception e) {
			e.printStackTrace();
			return 'F';
		}
		
		
	}
	
	protected ExceptionResponse getExceptionResponse(Exception ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode("NOT_FOUND");
		response.setErrorMessage(ex.getMessage());
		response.setTimestamp(LocalDateTime.now());
		return response;

	}

}
