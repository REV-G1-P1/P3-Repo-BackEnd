package com.revature.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.AccountInformation;
import com.revature.models.AccountType;
import com.revature.models.Transaction;
import com.revature.models.TransactionType;
import com.revature.models.User;
import com.revature.repositories.AccountInformationRepository;
import com.revature.repositories.UserRepository;

@Service
@Transactional
public class AccountInformationService {
	
	@Autowired
	private AccountInformationRepository accountInformationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

	public AccountInformation createAccount(AccountType accountType) {
        AccountInformation account = new AccountInformation();
		int randomAccount = (int) ((Math.random() * (99999999 - 11111111)) + 11111111);
        double randomBalance = Math.round(((Math.random() * (25000 - 100)) + 100) * 100.0) / 100.0;
		account.setAccountNumber(randomAccount);
		account.setRoutingNumber(867530900);
        account.setBalance(randomBalance);
        account.setAccountType(accountType);
		return accountInformationRepository.save(account);
	}
	
	public AccountInformation updateAccountBalance(Integer accountNumber, Double balance) {
		Optional<AccountInformation> tempOptionalAccountInfo = accountInformationRepository.findById(accountNumber);
        Integer userId = Integer.valueOf(session.getAttribute("CurrentUser").toString());
        Optional<User> user = userRepository.findById(userId);
		if(tempOptionalAccountInfo.isPresent() && user.isPresent()) {
            Double previousBalance = tempOptionalAccountInfo.get().getBalance();

            List<Transaction> transactionList = user.get().getTransactions();
            Transaction newTransaction = new Transaction();

            newTransaction.setAccountNumber(accountNumber);
            newTransaction.setAccountType(tempOptionalAccountInfo.get().getAccountType());
            newTransaction.setTransactionTime(LocalDateTime.now());
            newTransaction.setTransactionType(previousBalance > balance ? TransactionType.WITHDRAW : TransactionType.DEPOSIT);
            newTransaction.setBalanceChange(balance - previousBalance);

            transactionList.add(newTransaction);

			tempOptionalAccountInfo.get().setBalance(balance);
			return accountInformationRepository.save(tempOptionalAccountInfo.get());
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

    public boolean accountHolderVerification(Integer accountNumber) {
        Integer userId = Integer.valueOf(session.getAttribute("CurrentUser").toString());
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            List<AccountInformation> accInfo = user.get().getAccountInformation();
            for(AccountInformation i : accInfo) {
                if(i.getAccountNumber().equals(accountNumber)) {
                    return true;
                }
            }
        }
        return false;
    }
}