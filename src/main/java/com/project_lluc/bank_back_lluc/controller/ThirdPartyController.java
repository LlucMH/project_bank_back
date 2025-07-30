package com.project_lluc.bank_back_lluc.controller;

import com.project_lluc.bank_back_lluc.dto.ThirdPartyTransactionDTO;
import com.project_lluc.bank_back_lluc.service.interfaces.ThirdPartyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/third-party/accounts")
public class ThirdPartyController {

    private final ThirdPartyService thirdPartyService;

    public ThirdPartyController(ThirdPartyService thirdPartyService) {
        this.thirdPartyService = thirdPartyService;
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendMoney(
            @RequestHeader("hashed-key") String hashedKey,
            @RequestBody ThirdPartyTransactionDTO dto
    ) {
        thirdPartyService.sendMoney(
                hashedKey,
                dto.getAccountId(),
                dto.getSecretKey(),
                dto.getAmount()
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/receive")
    public ResponseEntity<Void> receiveMoney(
            @RequestHeader("hashed-key") String hashedKey,
            @RequestBody ThirdPartyTransactionDTO dto
    ) {
        thirdPartyService.receiveMoney(
                hashedKey,
                dto.getAccountId(),
                dto.getSecretKey(),
                dto.getAmount()
        );
        return ResponseEntity.ok().build();
    }
}
