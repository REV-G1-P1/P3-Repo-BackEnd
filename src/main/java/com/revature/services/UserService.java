package com.revature.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

	public User createUser(User user) {
        user.setUserRole(UserRole.CUSTOMER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setFailedLoginAttempts(0);
        user.setUserAccountLocked(false);
		return userRepository.save(user);
	}

	public User updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
	}

	public User findUserByEmail(String email) {
        Optional<User> tempUser = userRepository.findUserByEmail(email);
        if(tempUser.isPresent()) {
            return tempUser.get();
        }
        return null;
	}

	public Optional<User> findUserById(Integer id) {
		Optional<User> tempUser = userRepository.findById(id);
		if(tempUser.isPresent()) {
            return tempUser;
        }
        return Optional.empty();
	}

    public User findUserBySSN(Integer ssn) {
        Optional<User> tempUser = userRepository.findUserBySSN(ssn);
        if(tempUser.isPresent()) {
            return tempUser.get();
        }
        return null;
	}

}
