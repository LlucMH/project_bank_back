package com.project_lluc.bank_back_lluc.service.interfaces;

import com.project_lluc.bank_back_lluc.model.accounts.Account;
import com.project_lluc.bank_back_lluc.model.users.AccountHolder;
import com.project_lluc.bank_back_lluc.model.shared.Money;

import java.util.List;

public interface AccountHolderService {

    List<Account> getMyAccounts(AccountHolder accountHolder);
    void transfer(Long fromId, Long toId, String receiverName, String amount, AccountHolder owner);

    Money getAccountBalance(long accountId, String username);
}
