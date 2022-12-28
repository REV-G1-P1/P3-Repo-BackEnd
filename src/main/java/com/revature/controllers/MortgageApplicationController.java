package com.revature.controllers;

import java.util.Optional;

import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.LoanStatus;
import com.revature.models.MortgageApplication;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.services.MortgageApplicationService;
import com.revature.services.UserService;

@RestController
@RequestMapping("/mortgages")
@CrossOrigin(value = {"http://localhost:3000/", "http://p3-project-bucket.s3-website-us-east-1.amazonaws.com/"}, allowCredentials = "true")
public class MortgageApplicationController {
	
	@Autowired
	private MortgageApplicationService mortgageApplicationService;
	
	@Autowired
	private UserService userService;

    @Autowired
    private HttpSession session;
	
	@PostMapping("/create")
    public ResponseEntity<String> createMortgage(@RequestBody MortgageApplication mortgage){
        if(!session.getAttribute("CurrentUserRole").toString().equals("CUSTOMER")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if(session.getAttribute("CurrentUser") == null) {
            mortgageApplicationService.createMortgage(mortgage);
        } else {
            Integer userId = Integer.valueOf(session.getAttribute("CurrentUser").toString());
            Optional<User> user = userService.findUserById(userId);
            if(user.isPresent()) {
                mortgageApplicationService.createMortgage(mortgage, user.get());
            } else {
                return new ResponseEntity<>("Mortgage Application Failed to Create", HttpStatus.CONFLICT);
            }
        }
		return new ResponseEntity<>("Mortgage Application succesfully created", HttpStatus.CREATED);
    }
	
	@PutMapping("/process/{applicationId}")
	public ResponseEntity<String> approveOrDenyMortgage(@PathVariable Integer applicationId, @RequestBody String status) {
        if(!session.getAttribute("CurrentUserRole").toString().equals("MANAGER")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if(session.getAttribute("CurrentUser") == null) {
            return new ResponseEntity<>("Error processing the mortgage", HttpStatus.BAD_REQUEST);
        } else {
            Integer userId = Integer.valueOf(session.getAttribute("CurrentUser").toString());
            Optional<User> tempUser = userService.findUserById(userId);
            if(tempUser.isPresent()) {
                if(mortgageApplicationService.findMortgageByApplicationId(applicationId) != null 
                        && mortgageApplicationService.findMortgageByApplicationId(applicationId).getStatus().equals(LoanStatus.PENDING) 
                        && tempUser.get().getUserRole().equals(UserRole.MANAGER)) {
                    mortgageApplicationService.approveDenyMortgage(applicationId, status);
                    return new ResponseEntity<>("Mortgage application processing successful", HttpStatus.OK);
                }
                if(mortgageApplicationService.findMortgageByApplicationId(applicationId) == null) {
                    return new ResponseEntity<>("Mortgage application not found", HttpStatus.NOT_FOUND);
                }
                if(!tempUser.get().getUserRole().equals(UserRole.MANAGER)) {
                    return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
                }
                if(!mortgageApplicationService.findMortgageByApplicationId(applicationId).getStatus().equals(LoanStatus.PENDING)) {
                    return new ResponseEntity<>("Mortgage has already been processed", HttpStatus.FORBIDDEN);
                }
            }
        }
		return new ResponseEntity<>("Error processing the mortgage", HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/get/{applicationId}")
    public ResponseEntity<MortgageApplication> findMortgageById(@PathVariable Integer applicationId) {
        if(!session.getAttribute("CurrentUserRole").toString().equals("MANAGER")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
		MortgageApplication ma = mortgageApplicationService.findMortgageByApplicationId(applicationId);
        if(ma == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ma, HttpStatus.OK);
    }
}