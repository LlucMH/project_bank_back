package com.project_lluc.bank_back_lluc.service.interfaces;

import com.project_lluc.bank_back_lluc.model.users.AccountHolder;
import com.project_lluc.bank_back_lluc.model.users.Admin;
import com.project_lluc.bank_back_lluc.model.users.ThirdParty;
import com.project_lluc.bank_back_lluc.model.users.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);

    AccountHolder save(AccountHolder accountHolder);
    Admin save(Admin admin);
    ThirdParty save(ThirdParty thirdParty);
}
