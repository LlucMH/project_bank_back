package com.project_lluc.bank_back_lluc.controller;

import com.project_lluc.bank_back_lluc.model.accounts.Account;
import com.project_lluc.bank_back_lluc.model.users.AccountHolder;
import com.project_lluc.bank_back_lluc.service.interfaces.AccountHolderService;
import com.project_lluc.bank_back_lluc.service.interfaces.UserService;
import com.project_lluc.bank_back_lluc.model.shared.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account-holder")
@RequiredArgsConstructor
public class AccountHolderController {

    private final AccountHolderService accountHolderService;
    private final UserService userService;

    @GetMapping("/my-accounts")
    public ResponseEntity<List<Account>> getMyAccounts(@AuthenticationPrincipal UserDetails userDetails) {
        AccountHolder holder = (AccountHolder) userService.findByUsername(userDetails.getUsername()).orElseThrow();
        return ResponseEntity.ok(accountHolderService.getMyAccounts(holder));
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestParam Long fromId,
                                           @RequestParam Long toId,
                                           @RequestParam String receiverName,
                                           @RequestParam String amount,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        AccountHolder holder = (AccountHolder) userService.findByUsername(userDetails.getUsername()).orElseThrow();
        accountHolderService.transfer(fromId, toId, receiverName, amount, holder);
        return ResponseEntity.ok("Transfer successful");
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
