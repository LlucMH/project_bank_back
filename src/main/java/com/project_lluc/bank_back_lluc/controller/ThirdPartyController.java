package com.project_lluc.bank_back_lluc.controller;

import com.project_lluc.bank_back_lluc.dto.ThirdPartyTransactionDTO;
import com.project_lluc.bank_back_lluc.service.interfaces.ThirdPartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/third-party")
@RequiredArgsConstructor
public class ThirdPartyController {

    private final ThirdPartyService thirdPartyService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMoney(@RequestHeader("hashed-key") String hashedKey,
                                            @RequestBody ThirdPartyTransactionDTO dto) {
        thirdPartyService.sendMoney(hashedKey, dto.getAccountId(), dto.getSecretKey(), dto.getAmount());
        return ResponseEntity.ok("Money sent");
    }

    @PostMapping("/receive")
    public ResponseEntity<String> receiveMoney(@RequestHeader("hashed-key") String hashedKey,
                                               @RequestBody ThirdPartyTransactionDTO dto) {
        thirdPartyService.receiveMoney(hashedKey, dto.getAccountId(), dto.getSecretKey(), dto.getAmount());
        return ResponseEntity.ok("Money received");
    }
}
