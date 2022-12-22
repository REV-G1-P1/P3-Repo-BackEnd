package com.revature.controllers;

import java.util.ArrayList;
import java.util.List;
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

import com.revature.models.AccountInformation;
import com.revature.models.AccountType;
import com.revature.models.MortgageApplication;
import com.revature.models.User;
import com.revature.services.AccountInformationService;
import com.revature.services.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000/", allowCredentials = "true")
public class UserController {
	
    @Autowired
	private UserService userService;

    @Autowired
    private HttpSession session;

    @Autowired
	private AccountInformationService accountInformationService;

    @PostMapping("/validate")
    public ResponseEntity<String> validateUser(@RequestBody User user) {
        if(userService.findUserByEmail(user.getEmail()) == null && userService.findUserBySSN(user.getSSN()) == null){
            return new ResponseEntity<>("User is available", HttpStatus.OK);
        }
        return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
    }
		
	@PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        if(userService.findUserByEmail(user.getEmail()) == null && userService.findUserBySSN(user.getSSN()) == null){
            List<AccountInformation> accounts = new ArrayList<>();
            accounts.add(accountInformationService.createAccount(AccountType.CHECKING));
            accounts.add(accountInformationService.createAccount(AccountType.SAVINGS));
            user.setAccountInformation(accounts);
            userService.createUser(user);

            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
    }
	
	@PutMapping("/update")
	public ResponseEntity<String> updateUser(@RequestBody User user) {
        if(userService.findUserByEmail(user.getEmail()) != null) {
            User tempUser = userService.findUserByEmail(user.getEmail());
            if(user.getAddress() != null) {
                tempUser.setAddress(user.getAddress());
            }
            if(user.getFirstName() != null) {
                tempUser.setFirstName(user.getFirstName());
            }
            if(user.getLastName() != null) {
                tempUser.setLastName(user.getLastName());
            }
            if(user.getPassword() != null) {
                tempUser.setPassword(user.getPassword());
            }
            userService.updateUser(tempUser);

            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        }
		return new ResponseEntity<>("User does not exists", HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/get/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Integer id) {
        Optional<User> user = userService.findUserById(id);
        if(user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/get/currentuser")
    public ResponseEntity<User> findCurrentUser() {
        try {
            Optional<User> user = userService.findUserById(Integer.valueOf(session.getAttribute("CurrentUser").toString()));
            if(user.isPresent()) {
                return new ResponseEntity<>(user.get(), HttpStatus.OK);
            }
        } catch(Exception e) {

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	@GetMapping("/get/mortgages/{id}")
	public ResponseEntity<List<MortgageApplication>> getMortgageApplications(@PathVariable Integer id){
		User user = userService.findUserById(id);
		if(user != null){
			List<MortgageApplication> applications = new ArrayList<>();
			applications.addAll(user.getMortgageApplication());
			return new ResponseEntity<>(applications, HttpStatus.OK);
            
        }
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
