package com.revature.controllers;

import java.util.LinkedHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Claim;
import com.revature.models.LoanStatus;
import com.revature.models.MortgageApplication;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.services.MortgageApplicationService;

@RestController
@RequestMapping("/mortgages")
@CrossOrigin("http://localhost:3000/")
public class MortgageApplicationController {
	
	private MortgageApplicationService mortgageApplicationService;
	
	
	@PostMapping("/create")
    public ResponseEntity<MortgageApplication> createMortgage(@RequestBody MortgageApplication mortgage){
		return new ResponseEntity<>(mortgageApplicationService.createMortgage(mortgage), HttpStatus.CREATED);
    }
	
	//Keep an eye for testing user role
	@PutMapping("/process/{applicationId}")
	public ResponseEntity<String> approveOrDenyMortgage(@PathVariable Integer applicationId, @RequestBody String status, User user) {
		if(mortgageApplicationService.findMortgageByApplicationId(applicationId) != null 
				&& mortgageApplicationService.findMortgageByApplicationId(applicationId).getStatus().equals(LoanStatus.PENDING) 
				&& user.getUserRole().equals(UserRole.MANAGER)) {
			mortgageApplicationService.approveDenyMortgage(applicationId, status);
			return new ResponseEntity<>("Mortgage application processing successful", HttpStatus.OK);
		}
		return new ResponseEntity<>("Mortgage application not found", HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/get/{applicationId}")
    public ResponseEntity<MortgageApplication> findMortgageById(@PathVariable Integer applicationId) {
		MortgageApplication ma = mortgageApplicationService.findMortgageByApplicationId(applicationId);
        if(ma == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ma, HttpStatus.OK);
    }
	
	@DeleteMapping("/delete/{applicationId}")
	public ResponseEntity<String> deleteMortgageById(@PathVariable Integer applicationId) {
        if(mortgageApplicationService.findMortgageByApplicationId(applicationId) == null){
            return new ResponseEntity<>("Mortgage application does not exists", HttpStatus.NOT_FOUND);
        } else {
        	mortgageApplicationService.deleteMortgage(applicationId);
            return new ResponseEntity<>("Mortgage application has been deleted", HttpStatus.OK);
        }
    }

}
