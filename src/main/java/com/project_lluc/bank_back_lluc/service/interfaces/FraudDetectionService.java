package com.project_lluc.bank_back_lluc.service.interfaces;

public interface FraudDetectionService {
    boolean isFraudulentTransaction(Long accountId);
}
