package com.project_lluc.bank_back_lluc.service.impl;

import com.project_lluc.bank_back_lluc.model.accounts.*;
import com.project_lluc.bank_back_lluc.repository.AccountRepository;
import com.project_lluc.bank_back_lluc.service.interfaces.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Account getUpdatedBalance(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        applyInterestIfNeeded(account);
        applyPenaltyIfNeeded(account);
        return account;
    }

    @Override
    public void applyPenaltyIfNeeded(Account account) {
        if (account instanceof Checking checking) {
            if (checking.getBalance().getAmount().compareTo(new BigDecimal("250")) < 0) {
                checking.getBalance().decreaseAmount(checking.getPenaltyFee().getAmount());
            }
        } else if (account instanceof Savings savings) {
            if (savings.getBalance().getAmount().compareTo(savings.getMinimumBalance()) < 0) {
                savings.getBalance().decreaseAmount(savings.getPenaltyFee().getAmount());
            }
        }
    }

    @Override
    public void applyInterestIfNeeded(Account account) {
        if (account instanceof Savings savings) {
            long years = java.time.temporal.ChronoUnit.YEARS.between(savings.getLastInterestDate(), LocalDate.now());
            if (years >= 1) {
                BigDecimal interest = savings.getBalance().getAmount()
                        .multiply(savings.getInterestRate())
                        .multiply(BigDecimal.valueOf(years));
                savings.getBalance().increaseAmount(interest);
                savings.setLastInterestDate(LocalDate.now());
            }
        }

        if (account instanceof CreditCard card) {
            long months = java.time.temporal.ChronoUnit.MONTHS.between(card.getLastInterestDate(), LocalDate.now());
            if (months >= 1) {
                BigDecimal monthlyRate = card.getInterestRate().divide(BigDecimal.valueOf(12), 10, BigDecimal.ROUND_HALF_UP);
                BigDecimal interest = card.getBalance().getAmount()
                        .multiply(monthlyRate)
                        .multiply(BigDecimal.valueOf(months));
                card.getBalance().increaseAmount(interest);
                card.setLastInterestDate(LocalDate.now());
            }
        }
    }

    @Override
    public boolean hasSufficientFunds(Account account, BigDecimal amount) {
        return account.getBalance().getAmount().compareTo(amount) >= 0;
    }
}
