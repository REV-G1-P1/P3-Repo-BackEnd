package com.revature.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByEmail(String email);
    
}
