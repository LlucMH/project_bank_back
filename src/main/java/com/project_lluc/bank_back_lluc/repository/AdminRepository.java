package com.project_lluc.bank_back_lluc.repository;

import com.project_lluc.bank_back_lluc.model.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
