package com.project_lluc.bank_back_lluc.service.interfaces;

import java.math.BigDecimal;

public interface ThirdPartyService {

    void sendMoney(String hashedKey, Long accountId, String secretKey, BigDecimal amount);

    void receiveMoney(String hashedKey, Long accountId, String secretKey, BigDecimal amount);
}
