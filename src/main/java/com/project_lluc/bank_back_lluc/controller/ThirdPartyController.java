package com.project_lluc.bank_back_lluc.controller;

import com.project_lluc.bank_back_lluc.service.interfaces.ThirdPartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/third-party")
@RequiredArgsConstructor
public class ThirdPartyController {

    private final ThirdPartyService thirdPartyService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMoney(@RequestHeader("X-Hashed-Key") String hashedKey,
                                            @RequestParam Long accountId,
                                            @RequestParam String secretKey,
                                            @RequestParam BigDecimal amount) {
        thirdPartyService.sendMoney(hashedKey, accountId, secretKey, amount);
        return ResponseEntity.ok("Money sent");
    }

    @PostMapping("/receive")
    public ResponseEntity<String> receiveMoney(@RequestHeader("X-Hashed-Key") String hashedKey,
                                               @RequestParam Long accountId,
                                               @RequestParam String secretKey,
                                               @RequestParam BigDecimal amount) {
        thirdPartyService.receiveMoney(hashedKey, accountId, secretKey, amount);
        return ResponseEntity.ok("Money received");
    }
}

