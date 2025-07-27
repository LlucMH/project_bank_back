package com.project_lluc.bank_back_lluc.repository;

import com.project_lluc.bank_back_lluc.model.accounts.StudentChecking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCheckingRepository extends JpaRepository<StudentChecking, Long> {
}
