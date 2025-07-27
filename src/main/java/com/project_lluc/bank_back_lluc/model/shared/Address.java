package com.project_lluc.bank_back_lluc.model.shared;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Address {
    private String street;
    private String city;
    private String postalCode;
}
