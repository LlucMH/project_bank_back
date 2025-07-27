package com.project_lluc.bank_back_lluc.repository;

import com.project_lluc.bank_back_lluc.model.users.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {
}
