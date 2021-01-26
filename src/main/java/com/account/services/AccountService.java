package com.account.services;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.account.dto.AccountDto;
import com.account.model.AccountUser;

public interface AccountService {
	
	public ResponseEntity<List<AccountDto>> getAllAccount();

	public ResponseEntity<AccountUser> getAccount(long id, HttpHeaders headers);

	public void saveAccount(AccountUser account, HttpHeaders headers);

	public ResponseEntity<AccountDto> saveCustomerAccount(AccountDto accountDto, HttpHeaders headers);

	public ResponseEntity<AccountDto> updateAccount(AccountDto accountDto, long id);

	public ResponseEntity<Void> deleteAccount(long id, HttpHeaders headers);

	public char isMinor(Date dateOfBirth);
}
