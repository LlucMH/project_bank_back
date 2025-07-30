package com.project_lluc.bank_back_lluc.controller;

import com.project_lluc.bank_back_lluc.dto.ThirdPartyTransactionDTO;
import com.project_lluc.bank_back_lluc.service.interfaces.ThirdPartyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/third-party/accounts")
public class ThirdPartyController {

    private final ThirdPartyService service;

    public ThirdPartyController(ThirdPartyService service) {
        this.service = service;
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendMoney(
            @RequestHeader(name = "hashed-key", required = true) String hashedKey,
            @RequestBody ThirdPartyTransactionDTO dto
    ) {
        service.sendMoney(hashedKey, dto.getAccountId(), dto.getSecretKey(), dto.getAmount());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/receive")
    public ResponseEntity<Void> receiveMoney(
            @RequestHeader(name = "hashed-key", required = true) String hashedKey,
            @RequestBody ThirdPartyTransactionDTO dto
    ) {
        service.receiveMoney(hashedKey, dto.getAccountId(), dto.getSecretKey(), dto.getAmount());
        return ResponseEntity.ok().build();
    }
}

