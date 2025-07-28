package com.project_lluc.bank_back_lluc.service.impl;

import com.project_lluc.bank_back_lluc.service.interfaces.FraudDetectionService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class FraudDetectionServiceImpl implements FraudDetectionService {

    private final Map<Long, List<Instant>> transactionTimestamps = new HashMap<>();

    @Override
    public boolean isFraudulentTransaction(Long accountId) {
        Instant now = Instant.now();
        transactionTimestamps.putIfAbsent(accountId, new ArrayList<>());

        List<Instant> timestamps = transactionTimestamps.get(accountId);
        timestamps.add(now);

        timestamps.removeIf(timestamp -> now.minusSeconds(1).isAfter(timestamp));

        return timestamps.size() > 2;
    }
}
