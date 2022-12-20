package com.revature.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.MortgageApplication;
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
	
	@GetMapping("/get/{applicationId}")
    public ResponseEntity<MortgageApplication> findMortgageById(@PathVariable Integer applicationId) {
		MortgageApplication ma = mortgageApplicationService.findMortgageByApplicationId(applicationId);
        if(ma == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(ma, HttpStatus.OK);
        }
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
