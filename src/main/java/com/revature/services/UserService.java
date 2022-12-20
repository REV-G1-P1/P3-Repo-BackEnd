package com.revature.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.User;

@Service
@Transactional
public class UserService {

	public User createUser(User user) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
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
