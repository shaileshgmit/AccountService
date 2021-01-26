package com.account.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.account.dto.AccountDto;
import com.account.model.Account;
import com.account.model.AccountUser;
import com.account.services.AccountServiceImpl;


@RestController
public class AccountController {

	@Autowired
	private AccountServiceImpl accountService;
	
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@RequestMapping(value = "account", method = RequestMethod.GET)
	public ResponseEntity<List<AccountDto>> getAllAccount() {
		
		return accountService.getAllAccount();
	}

	@RequestMapping(value = "account/{id}", method = RequestMethod.GET)
	public ResponseEntity<AccountUser> getAccount(@PathVariable long id, @RequestHeader HttpHeaders headers) {
		return accountService.getAccount(id,headers);
	}

	@RequestMapping(value = "account", method = RequestMethod.POST)
	public void addAccount(@RequestBody AccountUser account, @RequestHeader HttpHeaders headers) {

		accountService.saveAccount(account,headers);

	}
	
	@RequestMapping(value = "accountadd", method = RequestMethod.POST)
	public ResponseEntity<AccountDto> addCustomerAccount(@RequestBody AccountDto accountDto, @RequestHeader HttpHeaders headers) {
		return accountService.saveCustomerAccount(accountDto,headers);

	}

	@PutMapping(value = "account/{id}")
	public ResponseEntity<AccountDto> updateAccount(@RequestBody AccountDto accountDto, @PathVariable("id") long id) {

		return accountService.updateAccount(accountDto, id);

	}

	@RequestMapping(value = "account/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void>  deleteAccount(@PathVariable long id, @RequestHeader HttpHeaders headers) {
		return accountService.deleteAccount(id,headers);

	}
}
