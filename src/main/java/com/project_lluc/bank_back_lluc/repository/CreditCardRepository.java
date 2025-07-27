package com.project_lluc.bank_back_lluc.repository;

import com.project_lluc.bank_back_lluc.model.accounts.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
