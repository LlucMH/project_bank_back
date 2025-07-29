package com.project_lluc.bank_back_lluc.service.impl;

import com.project_lluc.bank_back_lluc.model.accounts.Account;
import com.project_lluc.bank_back_lluc.model.users.AccountHolder;
import com.project_lluc.bank_back_lluc.repository.AccountRepository;
import com.project_lluc.bank_back_lluc.model.shared.Money;
import com.project_lluc.bank_back_lluc.service.interfaces.AccountHolderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountHolderServiceImpl implements AccountHolderService {

    private final AccountRepository accountRepository;

    @Override
    public List<Account> getMyAccounts(AccountHolder accountHolder) {
        return accountRepository.findByPrimaryOwner(accountHolder);
    }

    @Override
    public void transfer(Long fromId, Long toId, String receiverName, String amount, AccountHolder owner) {
        Account from = accountRepository.findById(fromId)
                .orElseThrow(() -> new IllegalArgumentException("Sender account not found"));
        Account to = accountRepository.findById(toId)
                .orElseThrow(() -> new IllegalArgumentException("Receiver account not found"));

        if (!from.getPrimaryOwner().getId().equals(owner.getId())
                && (from.getSecondaryOwner() == null || !from.getSecondaryOwner().getId().equals(owner.getId()))) {
            throw new IllegalArgumentException("You are not the owner of this account");
        }

        if (!to.getPrimaryOwner().getName().equalsIgnoreCase(receiverName)
                && (to.getSecondaryOwner() == null || !to.getSecondaryOwner().getName().equalsIgnoreCase(receiverName))) {
            throw new IllegalArgumentException("Receiver name does not match");
        }

        BigDecimal amountValue = new BigDecimal(amount);
        if (from.getBalance().getAmount().compareTo(amountValue) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        from.getBalance().decreaseAmount(amountValue);
        to.getBalance().increaseAmount(amountValue);

        accountRepository.save(from);
        accountRepository.save(to);
    }

    @Override
    public Money getAccountBalance(long accountId, String username) {
        return new Money(new BigDecimal("1000.00"));
    }

}
