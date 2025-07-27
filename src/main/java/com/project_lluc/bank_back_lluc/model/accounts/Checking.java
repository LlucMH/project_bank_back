package com.project_lluc.bank_back_lluc.model.accounts;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Checking extends Account {

    private String secretKey;

    @Column(name = "minimum_balance")
    private final String minimumBalance = "250";

    @Column(name = "monthly_maintenance_fee")
    private final String monthlyMaintenanceFee = "12";
}
