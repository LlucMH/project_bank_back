package com.project_lluc.bank_back_lluc.service.interfaces;

import com.project_lluc.bank_back_lluc.model.accounts.*;
import com.project_lluc.bank_back_lluc.model.shared.Money;

public interface AdminService {

    Checking createCheckingAccount(Checking account);
    StudentChecking createStudentCheckingAccount(StudentChecking account);
    Savings createSavingsAccount(Savings account);
    CreditCard createCreditCardAccount(CreditCard account);

    void updateBalance(Long accountId, String amount);

    Money getAccountBalance(long accountId);

    void deleteAccount(Long accountId);

    void updateAccountStatus(Long id, String status);

}
