package com.project_lluc.bank_back_lluc.controller;

import com.project_lluc.bank_back_lluc.model.accounts.CreditCard;
import com.project_lluc.bank_back_lluc.model.accounts.Savings;
import com.project_lluc.bank_back_lluc.model.accounts.Checking;
import com.project_lluc.bank_back_lluc.model.accounts.StudentChecking;
import com.project_lluc.bank_back_lluc.model.shared.Money;
import com.project_lluc.bank_back_lluc.service.interfaces.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/accounts/{id}/balance")
    public ResponseEntity<String> getAccountBalance(@PathVariable Long id) {
        Money balance = adminService.getAccountBalance(id);
        return ResponseEntity.ok(balance.getAmount().toString());
    }

    @PostMapping("/accounts/checking")
    public ResponseEntity<Checking> createChecking(@RequestBody Checking checking) {
        Checking created = adminService.createCheckingAccount(checking);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @PostMapping("/accounts/student-checking")
    public ResponseEntity<StudentChecking> createStudentChecking(@RequestBody StudentChecking studentChecking) {
        StudentChecking created = adminService.createStudentCheckingAccount(studentChecking);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @PostMapping("/accounts/savings")
    public ResponseEntity<Savings> createSavings(@RequestBody Savings savings) {
        Savings created = adminService.createSavingsAccount(savings);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @PostMapping("/accounts/credit-card")
    public ResponseEntity<CreditCard> createCreditCard(@RequestBody CreditCard creditCard) {
        CreditCard created = adminService.createCreditCardAccount(creditCard);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @PatchMapping("/accounts/{id}/balance")
    public ResponseEntity<String> updateBalance(
            @PathVariable Long id,
            @RequestBody Money newBalance) {
        adminService.updateBalance(id, newBalance.getAmount().toString());
        Money updated = adminService.getAccountBalance(id);
        return ResponseEntity.ok(updated.getAmount().toString());
    }

    @PatchMapping("/accounts/{id}/status")
    public ResponseEntity<String> updateAccountStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        adminService.updateAccountStatus(id, status);
        return ResponseEntity.ok("Status updated to " + status);
    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        adminService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
