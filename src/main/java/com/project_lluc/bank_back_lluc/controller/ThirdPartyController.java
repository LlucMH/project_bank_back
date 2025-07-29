package com.project_lluc.bank_back_lluc.controller;

import com.project_lluc.bank_back_lluc.dto.ThirdPartyTransactionDTO;
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
    public ResponseEntity<String> sendMoney(@RequestHeader("hashed-key") String hashedKey,
                                            @RequestBody ThirdPartyTransactionDTO dto) {
        // Aseguramos que los tipos coinciden con la interfaz
        Long accountId = dto.getAccountId();
        String secretKey = dto.getSecretKey();
        BigDecimal amount = dto.getAmount();

        thirdPartyService.sendMoney(hashedKey, accountId, secretKey, amount);
        return ResponseEntity.ok("Money sent");
    }

    @PostMapping("/receive")
    public ResponseEntity<String> receiveMoney(@RequestHeader("hashed-key") String hashedKey,
                                               @RequestBody ThirdPartyTransactionDTO dto) {
        // Aseguramos que los tipos coinciden con la interfaz
        Long accountId = dto.getAccountId();
        String secretKey = dto.getSecretKey();
        BigDecimal amount = dto.getAmount();

        thirdPartyService.receiveMoney(hashedKey, accountId, secretKey, amount);
        return ResponseEntity.ok("Money received");
    }
}
