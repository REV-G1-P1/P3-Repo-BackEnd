package com.revature.controllers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.revature.models.AccountInformation;
import com.revature.models.AccountType;
import com.revature.models.User;
import com.revature.services.AccountInformationService;
import com.revature.services.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin("http://localhost:3000/")
public class UserController {
	
    @Autowired
	private UserService userService;

    @Autowired
	private AccountInformationService accountInformationService;
		
	@PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody User user){
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
	public ResponseEntity<String> updateUser(@RequestBody LinkedHashMap<String, String> body){
		String email = body.get("email");
		String firstName = body.get("firstName");
		String lastName = body.get("lastName");
		String password = body.get("password");
		
		if(userService.findUserByEmail(email) != null){
            userService.updateUser(email, firstName, lastName, password);
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        }
		return new ResponseEntity<>("User does not exists", HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/update/address")
	public ResponseEntity<String> updateUserAddress(@RequestBody LinkedHashMap<String, String> body){
		String email = body.get("email");
		String streetAddress = body.get("streetAddress");
		String streetAddressLine2 = body.get("streetAddressLine2");
		String city = body.get("city");
		String state = body.get("state");
		Integer zipCode = Integer.parseInt(body.get("zipCode"));
		
		if(userService.findUserByEmail(email) != null){
            userService.updateUserAddress(email, streetAddress, streetAddressLine2, city, state, zipCode);
            return new ResponseEntity<>("User address updated successfully", HttpStatus.OK);
        }
		return new ResponseEntity<>("User does not exists", HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/get/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Integer id) {
        User user = userService.findUserById(id);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable Integer id) {
        if(userService.findUserById(id) == null){
            return new ResponseEntity<>("User does not exists", HttpStatus.NOT_FOUND);
        } else {
        	userService.deleteUser(id);
            return new ResponseEntity<>("User has been deleted", HttpStatus.OK);
        }
    }

}
