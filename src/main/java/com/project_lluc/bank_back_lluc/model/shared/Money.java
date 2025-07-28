package com.project_lluc.bank_back_lluc.model.shared;

import java.math.BigDecimal;
import java.util.Objects;

public class Money {

    private BigDecimal amount;

    public Money() {
    }

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void increaseAmount(BigDecimal toAdd) {
        this.amount = this.amount.add(toAdd);
    }

    public void decreaseAmount(BigDecimal toSubtract) {
        this.amount = this.amount.subtract(toSubtract);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money money)) return false;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
