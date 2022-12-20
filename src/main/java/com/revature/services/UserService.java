package com.revature.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.Address;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.repositories.UserRepository;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

	public User createUser(User user) {
        user.setUserRole(UserRole.CUSTOMER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public User updateUser(String email, String firstName, String lastName, String password) {
		Optional<User> tempOptionalUser = userRepository.findUserByEmail(email);
        User tempUser = new User();
        if(tempOptionalUser.isPresent()) {
            tempUser = tempOptionalUser.get();
        }
		tempUser.setFirstName(firstName);
		tempUser.setLastName(lastName);
		tempUser.setPassword(password);
		
		return userRepository.save(tempUser);
	}
	
	public User updateUserAddress(String email, String streetAddress, String streetAddressLine2, String city,
			String state, Integer zipCode) {
		
		Optional<User> tempOptionalUser = userRepository.findUserByEmail(email);
        User tempUser = new User();
        if(tempOptionalUser.isPresent()) {
            tempUser = tempOptionalUser.get();
        }
		Address tempUserAddress = tempUser.getAddress();
		tempUserAddress.setStreetAddress(streetAddress);
		tempUserAddress.setStreetAddressLine2(streetAddressLine2);
		tempUserAddress.setCity(city);
		tempUserAddress.setState(state);
		tempUserAddress.setZipCode(zipCode);
		tempUser.setAddress(tempUserAddress);
		
		return userRepository.save(tempUser);
	}
	
	public User findUserByEmail(String email) {
        Optional<User> tempUser = userRepository.findUserByEmail(email);
        if(tempUser.isPresent()) {
            return tempUser.get();
        }
        return null;
	}

	public User findUserById(Integer id) {
		Optional<User> tempUser = userRepository.findById(id);
		if(tempUser.isPresent()) {
            return tempUser.get();
        }
        return null;
	}

	public void deleteUser(Integer id) {
		userRepository.deleteById(id);
		
	}

    public User findUserBySSN(Integer ssn) {
        Optional<User> tempUser = userRepository.findUserBySSN(ssn);
        if(tempUser.isPresent()) {
            return tempUser.get();
        }
        return null;
	}

}
