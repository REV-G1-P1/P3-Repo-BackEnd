package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    
}
