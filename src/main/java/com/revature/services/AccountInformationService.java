package com.revature.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.AccountInformation;
import com.revature.models.AccountType;
import com.revature.repositories.AccountInformationRepository;

@Service
@Transactional
public class AccountInformationService {
	
	@Autowired
	private AccountInformationRepository accountInformationRepository;

	public AccountInformation createAccount(AccountType accountType) {
        AccountInformation account = new AccountInformation();
		int randomAccount = (int) ((Math.random() * (99999999 - 11111111)) + 11111111);
        double randomBalance = Math.round(((Math.random() * (250000 - 100)) + 100) * 100.0) / 100.0;
		account.setAccountNumber(randomAccount);
		account.setRoutingNumber(867530900);
        account.setBalance(randomBalance);
        account.setAccountType(accountType);
		return accountInformationRepository.save(account);
	}
	
	public AccountInformation updateAccountBalance(Integer accountNumber, Double balance) {
		Optional<AccountInformation> tempOptionalAccountInfo = accountInformationRepository.findById(accountNumber);
		AccountInformation tempAccountInfo = new AccountInformation();
		if(tempOptionalAccountInfo.isPresent()) {
			tempAccountInfo = tempOptionalAccountInfo.get();
			tempAccountInfo.setBalance(balance);
			return accountInformationRepository.save(tempAccountInfo);
		}
		return null;
	}

	public AccountInformation findAccountByAccountNumber(Integer accountNumber) {
		Optional<AccountInformation> tempAccountInfo = accountInformationRepository.findById(accountNumber);
		if(tempAccountInfo.isPresent()) {
            return tempAccountInfo.get();
        }
        return null;
	}

}
