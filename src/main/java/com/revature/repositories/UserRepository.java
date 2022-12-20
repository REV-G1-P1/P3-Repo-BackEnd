package com.revature.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.revature.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserBySSN(Integer ssn);
    
    @Query(value = "SELECT attribute_bytes FROM spring_session_attributes ssa JOIN spring_session ss ON ssa.session_primary_id = ss.primary_id WHERE session_id = ?1", nativeQuery = true)
    List<byte[]> getSessionAttributesById(String id);
    
    @Query(value = "SELECT session_id FROM spring_session WHERE session_id = ?1", nativeQuery = true)
    String getSessionById(String id);

    @Modifying
    @Query(value = "DELETE FROM spring_session WHERE session_id = ?1", nativeQuery = true)
    void removeSessionById(String cookieId);

    
}