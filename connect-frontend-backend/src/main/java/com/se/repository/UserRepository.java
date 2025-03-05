package com.se.repository;

import com.se.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query for finding a user by email
    User findByEmail(String email);
}
