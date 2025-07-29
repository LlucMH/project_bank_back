package com.project_lluc.bank_back_lluc.controller;

import com.project_lluc.bank_back_lluc.model.accounts.CreditCard;
import com.project_lluc.bank_back_lluc.model.accounts.Savings;
import com.project_lluc.bank_back_lluc.model.accounts.Checking;
import com.project_lluc.bank_back_lluc.model.accounts.StudentChecking;
import com.project_lluc.bank_back_lluc.model.shared.Money;
import com.project_lluc.bank_back_lluc.service.interfaces.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /**
     * Obtiene el balance de la cuenta con id dado.
     */
    @GetMapping("/accounts/{id}/balance")
    public ResponseEntity<String> getAccountBalance(@PathVariable Long id) {
        Money balance = adminService.getAccountBalance(id);
        return ResponseEntity.ok(balance.getAmount().toString());
    }

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

    /**
     * Actualiza el balance de la cuenta.
     */
    @PatchMapping("/accounts/{id}/balance")
    public ResponseEntity<String> updateBalance(
            @PathVariable Long id,
            @RequestBody Money newBalance) {
        adminService.updateBalance(id, newBalance.getAmount().toString());
        Money updated = adminService.getAccountBalance(id);
        return ResponseEntity.ok(updated.getAmount().toString());
    }

    /**
     * Actualiza el estado de la cuenta (e.g. ACTIVE, FROZEN).
     */
    @PatchMapping("/accounts/{id}/status")
    public ResponseEntity<String> updateAccountStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        adminService.updateAccountStatus(id, status);
        return ResponseEntity.ok("Status updated to " + status);
    }

    /**
     * Elimina la cuenta con el id dado.
     */
    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        adminService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
