package com.project_lluc.bank_back_lluc.repository;

import com.project_lluc.bank_back_lluc.model.accounts.Account;
import com.project_lluc.bank_back_lluc.model.users.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByPrimaryOwner(AccountHolder accountHolder);
}
