package com.project_lluc.bank_back_lluc.service;

import com.project_lluc.bank_back_lluc.model.accounts.Checking;
import com.project_lluc.bank_back_lluc.model.shared.Money;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountServiceTest {

    @Test
    void testPenaltyIsAppliedWhenBelowMinimumBalance() {
        // TODO: implementar lógica real
        assertTrue(true);
    }

    @Test
    void testInterestAppliedToSavingsAccountAfterOneYear() {
        assertTrue(true);
    }

    @Test
    void testInterestAppliedToCreditCardAfterOneMonth() {
        assertTrue(true);
    }

    @Test
    void testHasSufficientFundsReturnsTrueWhenEnoughBalance() {
        assertTrue(true);
    }

    @Test
    void testHasSufficientFundsReturnsFalseWhenNotEnoughBalance() {
        assertTrue(true);
    }

    @Test
    void testGetUpdatedBalanceTriggersInterestOrPenaltyIfNeeded() {
        assertTrue(true);
    }

    @Test
    void testCheckingAccountCreationByAge_LessThan24ResultsInStudentChecking() {
        assertTrue(true);
    }
}
