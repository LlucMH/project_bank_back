package com.project_lluc.bank_back_lluc.model.shared;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Money {

    private BigDecimal amount;
    private String currency = "EUR";

    public Money(String amount) {
        this.amount = new BigDecimal(amount);
    }

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public void increaseAmount(BigDecimal addition) {
        this.amount = this.amount.add(addition);
    }

    public void decreaseAmount(BigDecimal subtraction) {
        this.amount = this.amount.subtract(subtraction);
    }
}
