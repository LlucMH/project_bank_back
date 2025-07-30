package com.project_lluc.bank_back_lluc.controller;

import com.project_lluc.bank_back_lluc.dto.ThirdPartyTransactionDTO;
import com.project_lluc.bank_back_lluc.service.interfaces.ThirdPartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/third-party")
@RequiredArgsConstructor
public class ThirdPartyController {

    private final ThirdPartyService thirdPartyService;

    @PostMapping("/accounts/send")
    public ResponseEntity<Void> sendMoney(
            @RequestHeader("hashed-key") String hashedKey,
            @RequestBody @Valid ThirdPartyTransactionDTO dto) {

        thirdPartyService.sendMoney(
                hashedKey,
                dto.getAccountId(),
                dto.getSecretKey(),
                dto.getAmount()
        );
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/accounts/receive")
    public ResponseEntity<Void> receiveMoney(
            @RequestHeader("hashed-key") String hashedKey,
            @RequestBody @Valid ThirdPartyTransactionDTO dto) {

        thirdPartyService.receiveMoney(
                hashedKey,
                dto.getAccountId(),
                dto.getSecretKey(),
                dto.getAmount()
        );
        return ResponseEntity.noContent().build();
    }
}
