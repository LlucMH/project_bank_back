package com.project_lluc.bank_back_lluc.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ThirdPartyTransactionDTO {
    private Long accountId;
    private String secretKey;
    private BigDecimal amount;
}
