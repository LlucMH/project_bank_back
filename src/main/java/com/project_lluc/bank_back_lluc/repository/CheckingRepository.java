package com.project_lluc.bank_back_lluc.repository;

import com.project_lluc.bank_back_lluc.model.accounts.Checking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckingRepository extends JpaRepository<Checking, Long> {
}
