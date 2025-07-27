package com.project_lluc.bank_back_lluc.model.accounts;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CreditCard extends Account {

    @Column(name = "interest_rate")
    private BigDecimal interestRate = new BigDecimal("0.2");

    @Column(name = "credit_limit")
    private BigDecimal creditLimit = new BigDecimal("100");

    private LocalDate lastInterestDate = LocalDate.now();
}
