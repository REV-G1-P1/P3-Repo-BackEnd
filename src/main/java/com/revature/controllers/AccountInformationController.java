package com.revature.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.services.AccountInformationService;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(value = {"http://localhost:3000/", "http://p3-project-bucket.s3-website-us-east-1.amazonaws.com/"}, allowCredentials = "true")
public class AccountInformationController {
	
	@Autowired
	private AccountInformationService accountInformationService;
	
	@PutMapping("/update/balance")
	public ResponseEntity<String> updateAccountBalance(@RequestBody Map<String, String> body){
		Integer accountNumber = Integer.parseInt(body.get("accountNumber"));
		Double balance = Double.parseDouble(body.get("balance"));
		if(accountInformationService.findAccountByAccountNumber(accountNumber) != null) {
            if(accountInformationService.accountHolderVerification(accountNumber)) {
                accountInformationService.updateAccountBalance(accountNumber, balance);
                return new ResponseEntity<>("Account balance updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("You are not the Account Holder", HttpStatus.UNAUTHORIZED);
            }
        }
		return new ResponseEntity<>("Account does not exists", HttpStatus.NOT_FOUND);
	}
}
