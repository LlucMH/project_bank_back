package com.project_lluc.bank_back_lluc.model.accounts;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Savings extends Account {

    private String secretKey;

    @Column(name = "minimum_balance")
    private BigDecimal minimumBalance = new BigDecimal("1000");

    @Column(name = "interest_rate")
    private BigDecimal interestRate = new BigDecimal("0.0025");

    private LocalDate lastInterestDate = LocalDate.now();
}
