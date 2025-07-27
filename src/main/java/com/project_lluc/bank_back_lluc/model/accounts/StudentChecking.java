package com.project_lluc.bank_back_lluc.model.accounts;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StudentChecking extends Account {
    private String secretKey;
}
