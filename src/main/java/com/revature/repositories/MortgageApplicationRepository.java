package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.MortgageApplication;

public interface MortgageApplicationRepository extends JpaRepository<MortgageApplication, Integer> {
    
}
