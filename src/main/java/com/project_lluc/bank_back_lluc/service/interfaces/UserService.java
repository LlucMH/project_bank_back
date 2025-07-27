package com.project_lluc.bank_back_lluc.service.interfaces;

import com.project_lluc.bank_back_lluc.model.users.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
}
