package com.project_lluc.bank_back_lluc.service.impl;

import com.project_lluc.bank_back_lluc.model.accounts.Account;
import com.project_lluc.bank_back_lluc.repository.AccountRepository;
import com.project_lluc.bank_back_lluc.repository.ThirdPartyRepository;
import com.project_lluc.bank_back_lluc.service.interfaces.ThirdPartyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class ThirdPartyServiceImpl implements ThirdPartyService {

    private final ThirdPartyRepository thirdPartyRepository;
    private final AccountRepository accountRepository;

    @Override
    public void sendMoney(String hashedKey, Long accountId, String secretKey, BigDecimal amount) {
        var thirdParty = thirdPartyRepository.findByHashedKey(hashedKey)
                .orElseThrow(() -> new IllegalArgumentException("Invalid hashed key"));

        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (!validateSecretKey(account, secretKey)) {
            throw new IllegalArgumentException("Invalid account secret key");
        }

        account.getBalance().decreaseAmount(amount);
        accountRepository.save(account);
    }

    @Override
    public void receiveMoney(String hashedKey, Long accountId, String secretKey, BigDecimal amount) {
        var thirdParty = thirdPartyRepository.findByHashedKey(hashedKey)
                .orElseThrow(() -> new IllegalArgumentException("Invalid hashed key"));

        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (!validateSecretKey(account, secretKey)) {
            throw new IllegalArgumentException("Invalid account secret key");
        }

        account.getBalance().increaseAmount(amount);
        accountRepository.save(account);
    }

    private boolean validateSecretKey(Account account, String secretKey) {
        if (account instanceof com.project_lluc.bank_back_lluc.model.accounts.Checking checking) {
            return checking.getSecretKey().equals(secretKey);
        }
        if (account instanceof com.project_lluc.bank_back_lluc.model.accounts.StudentChecking student) {
            return student.getSecretKey().equals(secretKey);
        }
        if (account instanceof com.project_lluc.bank_back_lluc.model.accounts.Savings savings) {
            return savings.getSecretKey().equals(secretKey);
        }
        return false;
    }
}
