package com.revature.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.AccountInformation;
import com.revature.models.User;
import com.revature.repositories.AccountInformationRepository;

@Service
@Transactional
public class AccountInformationService {
	
	@Autowired
	private AccountInformationRepository accountInformationRepository;

	public AccountInformation createAccount(AccountInformation account) {
		int random = (int) ((Math.random() * (99999999 - 11111111)) + 11111111);
		account.setAccountNumber(random);
		account.setRoutingNumber(876389223);
		return accountInformationRepository.save(account);
	}

	public AccountInformation updateAccountName(Integer accountNumber, String accountName) {
		Optional<AccountInformation> tempOptionalAccountInfo = accountInformationRepository.findById(accountNumber);
		AccountInformation tempAccountInfo = new AccountInformation();
		if(tempOptionalAccountInfo.isPresent()) {
			tempAccountInfo = tempOptionalAccountInfo.get();
			tempAccountInfo.setAccountName(accountName);
			return accountInformationRepository.save(tempAccountInfo);
		}
		return null;
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

	public void deleteAccount(Integer accountNumber) {
		accountInformationRepository.deleteById(accountNumber);
		
	}

}
