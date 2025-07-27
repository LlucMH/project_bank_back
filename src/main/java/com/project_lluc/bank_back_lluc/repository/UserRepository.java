package com.project_lluc.bank_back_lluc.repository;

import com.project_lluc.bank_back_lluc.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
