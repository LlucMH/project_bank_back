package com.project_lluc.bank_back_lluc.repository;

import com.project_lluc.bank_back_lluc.model.accounts.Savings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsRepository extends JpaRepository<Savings, Long> {
}
