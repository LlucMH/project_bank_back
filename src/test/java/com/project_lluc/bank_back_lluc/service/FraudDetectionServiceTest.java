package com.project_lluc.bank_back_lluc.service;

import com.project_lluc.bank_back_lluc.model.accounts.Account;
import com.project_lluc.bank_back_lluc.model.enums.Status;
import com.project_lluc.bank_back_lluc.service.impl.FraudDetectionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FraudDetectionServiceTest {

    private FraudDetectionServiceImpl fraudService;

    @BeforeEach
    void setUp() {
        fraudService = new FraudDetectionServiceImpl();
    }

    @Test
    void testMultipleTransactionsInOneSecondFlagsFraud() {
        Account account = new Account() {}; // instancia anónima de clase abstracta
        account.setStatus(Status.ACTIVE);

        LocalDateTime now = LocalDateTime.now();

        List<LocalDateTime> transactionTimestamps = Arrays.asList(
                now,
                now.plusNanos(100_000_000), // 100 ms
                now.plusNanos(200_000_000)  // 200 ms
        );

        fraudService.detectRapidTransactions(account, transactionTimestamps);

        assertEquals(Status.FROZEN, account.getStatus());
    }

    @Test
    void testDailyLimitExceededFlagsFraud() {
        Account account = new Account() {};
        account.setStatus(Status.ACTIVE);

        BigDecimal previousMax = new BigDecimal("1000");
        BigDecimal todayTotal = new BigDecimal("1600"); // 160% de 1000

        fraudService.detectUnusualDailyVolume(account, todayTotal, previousMax);

        assertEquals(Status.FROZEN, account.getStatus());
    }

    @Test
    void testNoFraudIfTransactionsAreNormal() {
        Account account = new Account() {};
        account.setStatus(Status.ACTIVE);

        LocalDateTime now = LocalDateTime.now();

        List<LocalDateTime> normalTimestamps = Arrays.asList(
                now.minusSeconds(10),
                now.minusSeconds(5),
                now
        );

        fraudService.detectRapidTransactions(account, normalTimestamps);

        BigDecimal previousMax = new BigDecimal("1000");
        BigDecimal todayTotal = new BigDecimal("1200"); // solo 120%

        fraudService.detectUnusualDailyVolume(account, todayTotal, previousMax);

        assertEquals(Status.ACTIVE, account.getStatus());
    }
}
