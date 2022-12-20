package com.revature.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.AccountInformation;

@Service
@Transactional
public class AccountInformationService {

	public AccountInformation createAccount(AccountInformation account) {
		// TODO Auto-generated method stub
		return null;
	}

	public AccountInformation updateAccountName(Integer accountNumber, String accountName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public AccountInformation updateAccountBalance(Integer accountNumber, Double balance) {
		// TODO Auto-generated method stub
		return null;
	}

	public AccountInformation findAccountByAccountNumber(Integer accountNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteAccount(Integer accountNumber) {
		// TODO Auto-generated method stub
		
	}

}
