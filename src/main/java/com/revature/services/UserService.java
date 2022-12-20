package com.revature.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.repositories.UserRepository;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

	public User createUser(User user) {
        user.setUserRole(UserRole.CUSTOMER);
		return userRepository.save(user);
	}

	public User updateUser(String email, String firstName, String lastName, String password) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public User updateUserAddress(String email, String streetAddress, String streetAddressLine2, String city,
			String state, Integer zipCode) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public User findUserByEmail(String email) {
        Optional<User> tempUser = userRepository.findUserByEmail(email);
        if(tempUser.isPresent()) {
            return tempUser.get();
        }
        return null;
	}

	public User findUserById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteUser(Integer id) {
		// TODO Auto-generated method stub
		
	}

}
