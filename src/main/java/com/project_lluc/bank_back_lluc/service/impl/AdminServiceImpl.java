package com.project_lluc.bank_back_lluc.service.impl;

import com.project_lluc.bank_back_lluc.model.accounts.*;
import com.project_lluc.bank_back_lluc.repository.*;
import com.project_lluc.bank_back_lluc.service.interfaces.AdminService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.project_lluc.bank_back_lluc.model.shared.Money;
import jakarta.persistence.EntityNotFoundException;
import com.project_lluc.bank_back_lluc.model.enums.Status;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final CheckingRepository checkingRepository;
    private final StudentCheckingRepository studentCheckingRepository;
    private final SavingsRepository savingsRepository;
    private final CreditCardRepository creditCardRepository;
    private final AccountRepository accountRepository;

    @Override
    public Checking createCheckingAccount(Checking account) {
        return checkingRepository.save(account);
    }

    @Override
    public StudentChecking createStudentCheckingAccount(StudentChecking account) {
        return studentCheckingRepository.save(account);
    }

    @Override
    public Savings createSavingsAccount(Savings account) {
        return savingsRepository.save(account);
    }

    @Override
    public CreditCard createCreditCardAccount(CreditCard account) {
        return creditCardRepository.save(account);
    }

    @Override
    public void updateBalance(Long accountId, String amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        account.getBalance().setAmount(new BigDecimal(amount));
        accountRepository.save(account);
    }

    @Override
    public Money getAccountBalance(long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Account not found: " + accountId));
        return account.getBalance();
    }

    @Override
    public void deleteAccount(Long accountId) {
        accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found: " + accountId));
        accountRepository.deleteById(accountId);
    }

    @Override
    public void updateAccountStatus(Long id, String status) {
        Account acc = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found: " + id));
        acc.setStatus(Status.valueOf(status));
        accountRepository.save(acc);
    }

}
