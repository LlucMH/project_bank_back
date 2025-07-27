package com.project_lluc.bank_back_lluc.service.interfaces;

import com.project_lluc.bank_back_lluc.model.accounts.*;

public interface AdminService {

    Checking createCheckingAccount(Checking account);
    StudentChecking createStudentCheckingAccount(StudentChecking account);
    Savings createSavingsAccount(Savings account);
    CreditCard createCreditCardAccount(CreditCard account);

    void updateBalance(Long accountId, String amount);
}
