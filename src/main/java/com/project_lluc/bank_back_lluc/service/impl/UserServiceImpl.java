package com.project_lluc.bank_back_lluc.service.impl;

import com.project_lluc.bank_back_lluc.model.users.User;
import com.project_lluc.bank_back_lluc.service.interfaces.UserService;
import com.project_lluc.bank_back_lluc.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

}
