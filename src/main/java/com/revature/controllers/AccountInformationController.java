package com.revature.controllers;

import java.util.LinkedHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.AccountInformation;
import com.revature.services.AccountInformationService;

@RestController
@RequestMapping("/accounts")
@CrossOrigin("http://localhost:3000/")
public class AccountInformationController {
	
	private AccountInformationService accountInformationService;
	
	
	@PostMapping("/create")
    public ResponseEntity<AccountInformation> createAccount(@RequestBody AccountInformation account){
		return new ResponseEntity<>(accountInformationService.createAccount(account), HttpStatus.CREATED);
    }
	
	@PutMapping("/update/nickname")
	public ResponseEntity<String> updateAccountName(@RequestBody LinkedHashMap<String, String> body){
		Integer accountNumber = Integer.parseInt(body.get("accountNumber"));
		String accountName = body.get("accountName");
		if(accountInformationService.findAccountByAccountNumber(accountNumber) != null){
			accountInformationService.updateAccountName(accountNumber, accountName);
            return new ResponseEntity<>("Account name updated successfully", HttpStatus.OK);
        }
		else
			return new ResponseEntity<>("Account does not exists", HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/update/balance")
	public ResponseEntity<String> updateAccountBalance(@RequestBody LinkedHashMap<String, String> body){
		Integer accountNumber = Integer.parseInt(body.get("accountNumber"));
		Double balance = Double.parseDouble(body.get("balance"));
		if(accountInformationService.findAccountByAccountNumber(accountNumber) != null){
			accountInformationService.updateAccountBalance(accountNumber, balance);
            return new ResponseEntity<>("Account balance updated successfully", HttpStatus.OK);
        }
		else
			return new ResponseEntity<>("Account does not exists", HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/get")
    public ResponseEntity<AccountInformation> findAccountByAccountNumber(@RequestBody Integer accountNumber) {
		AccountInformation ai = accountInformationService.findAccountByAccountNumber(accountNumber);
        if(ai == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(ai, HttpStatus.OK);
        }
    }
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteAccountByAccountNumber(@RequestBody Integer accountNumber) {
        if(accountInformationService.findAccountByAccountNumber(accountNumber) == null){
            return new ResponseEntity<>("Account does not exists", HttpStatus.NOT_FOUND);
        } else {
        	accountInformationService.deleteAccount(accountNumber);
            return new ResponseEntity<>("Account has been deleted", HttpStatus.OK);
        }
    }

}
