package com.project_lluc.bank_back_lluc.controller;

import com.project_lluc.bank_back_lluc.model.accounts.*;
import com.project_lluc.bank_back_lluc.service.interfaces.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/checking")
    public ResponseEntity<Checking> createChecking(@RequestBody Checking checking) {
        return ResponseEntity.ok(adminService.createCheckingAccount(checking));
    }

    @PostMapping("/student-checking")
    public ResponseEntity<StudentChecking> createStudentChecking(@RequestBody StudentChecking studentChecking) {
        return ResponseEntity.ok(adminService.createStudentCheckingAccount(studentChecking));
    }

    @PostMapping("/savings")
    public ResponseEntity<Savings> createSavings(@RequestBody Savings savings) {
        return ResponseEntity.ok(adminService.createSavingsAccount(savings));
    }

    @PostMapping("/credit-card")
    public ResponseEntity<CreditCard> createCreditCard(@RequestBody CreditCard creditCard) {
        return ResponseEntity.ok(adminService.createCreditCardAccount(creditCard));
    }

    @PatchMapping("/accounts/{id}/balance")
    public ResponseEntity<String> updateBalance(@PathVariable Long id, @RequestParam String amount) {
        adminService.updateBalance(id, amount);
        return ResponseEntity.ok("Balance updated successfully.");
    }
}
