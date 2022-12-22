package com.revature.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.LoanStatus;
import com.revature.models.MortgageApplication;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.services.MortgageApplicationService;
import com.revature.services.UserService;

@RestController
@RequestMapping("/mortgages")
@CrossOrigin("http://localhost:3000/")
public class MortgageApplicationController {
	
	@Autowired
	private MortgageApplicationService mortgageApplicationService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/create/{userId}")
    public ResponseEntity<String> createMortgage(@PathVariable Integer userId, @RequestBody MortgageApplication mortgage){
		// If userId = 0 = Guest
		if(userId != 0) {
			User user = userService.findUserById(userId);
			mortgageApplicationService.createMortgage(mortgage, user);
		}
		else
			mortgageApplicationService.createMortgage(mortgage);
		
		return new ResponseEntity<>("Mortgage Application succesfully created", HttpStatus.CREATED);
    }
	
	@PutMapping("/process/{applicationId}")
	public ResponseEntity<String> approveOrDenyMortgage(@PathVariable Integer applicationId, @RequestBody String status, @RequestParam("userId") Integer userId) {
		User user = userService.findUserById(userId);
		if(mortgageApplicationService.findMortgageByApplicationId(applicationId) != null 
				&& mortgageApplicationService.findMortgageByApplicationId(applicationId).getStatus().equals(LoanStatus.PENDING) 
				&& user.getUserRole().equals(UserRole.MANAGER)) {
			mortgageApplicationService.approveDenyMortgage(applicationId, status);
			return new ResponseEntity<>("Mortgage application processing successful", HttpStatus.OK);
		}
		if(mortgageApplicationService.findMortgageByApplicationId(applicationId) == null) {
			return new ResponseEntity<>("Mortgage application not found", HttpStatus.NOT_FOUND);
		}
		if(!user.getUserRole().equals(UserRole.MANAGER)) {
			return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
		}
		if(!mortgageApplicationService.findMortgageByApplicationId(applicationId).getStatus().equals(LoanStatus.PENDING)) {
			return new ResponseEntity<>("Mortgage has already been processed", HttpStatus.UNAUTHORIZED);
		}
		
		return new ResponseEntity<>("Error processing the mortgage", HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/get/{applicationId}")
    public ResponseEntity<MortgageApplication> findMortgageById(@PathVariable Integer applicationId) {
		MortgageApplication ma = mortgageApplicationService.findMortgageByApplicationId(applicationId);
        if(ma == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ma, HttpStatus.OK);
    }

}
