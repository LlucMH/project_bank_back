package com.project_lluc.bank_back_lluc.controller;

import com.project_lluc.bank_back_lluc.model.accounts.Account;
import com.project_lluc.bank_back_lluc.model.users.AccountHolder;
import com.project_lluc.bank_back_lluc.model.shared.Money;
import com.project_lluc.bank_back_lluc.service.interfaces.AccountHolderService;
import com.project_lluc.bank_back_lluc.service.interfaces.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/account-holder")
@RequiredArgsConstructor
public class AccountHolderController {

    private final AccountHolderService accountHolderService;
    private final UserService userService;

    @GetMapping("/my-accounts")
    public ResponseEntity<List<Account>> getMyAccounts(
            @AuthenticationPrincipal UserDetails userDetails) {
        AccountHolder holder = (AccountHolder) userService
                .findByUsername(userDetails.getUsername())
                .orElseThrow();
        return ResponseEntity.ok(accountHolderService.getMyAccounts(holder));
    }

    @Data
    public static class TransferRequest {
        private Long fromId;
        private Long toId;
        private String receiverName;
        private String amount;
    }

    @PostMapping("/accounts/transfer")
    public ResponseEntity<Void> transfer(
            @RequestBody @Valid TransferRequest req,
            @AuthenticationPrincipal UserDetails userDetails) {

        AccountHolder holder = (AccountHolder) userService
                .findByUsername(userDetails.getUsername())
                .orElseThrow();

        accountHolderService.transfer(
                req.getFromId(),
                req.getToId(),
                req.getReceiverName(),
                req.getAmount(),
                holder
        );

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/accounts/{id}/balance")
    public ResponseEntity<String> getAccountBalance(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        AccountHolder holder = (AccountHolder) userService
                .findByUsername(userDetails.getUsername())
                .orElseThrow();
        Money balance = accountHolderService.getAccountBalance(id, holder.getUsername());
        return ResponseEntity.ok(balance.getAmount().toString());
    }
}
