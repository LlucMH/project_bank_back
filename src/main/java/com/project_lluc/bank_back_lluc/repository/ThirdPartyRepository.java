package com.project_lluc.bank_back_lluc.repository;

import com.project_lluc.bank_back_lluc.model.users.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ThirdPartyRepository extends JpaRepository<ThirdParty, Long> {
    Optional<ThirdParty> findByHashedKey(String hashedKey);
}

