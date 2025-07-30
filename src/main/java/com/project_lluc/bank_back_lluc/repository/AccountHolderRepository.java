package com.project_lluc.bank_back_lluc.repository;

import com.project_lluc.bank_back_lluc.model.users.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {
    Optional<AccountHolder> findByUsername(String username);
}
