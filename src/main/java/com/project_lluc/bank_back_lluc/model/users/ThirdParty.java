package com.project_lluc.bank_back_lluc.model.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ThirdParty extends User {

    @Column(unique = true, nullable = false)
    private String hashedKey;
}
