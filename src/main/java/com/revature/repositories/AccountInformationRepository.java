package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.AccountInformation;

public interface AccountInformationRepository extends JpaRepository<AccountInformation, Integer> {
    
}
