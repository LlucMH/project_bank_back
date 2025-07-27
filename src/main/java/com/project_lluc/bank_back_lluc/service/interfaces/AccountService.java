package com.project_lluc.bank_back_lluc.service.interfaces;

import com.project_lluc.bank_back_lluc.model.accounts.Account;

import java.math.BigDecimal;

public interface AccountService {

    Account getUpdatedBalance(Long accountId);

    void applyPenaltyIfNeeded(Account account);

    void applyInterestIfNeeded(Account account);

    boolean hasSufficientFunds(Account account, BigDecimal amount);
}
