package com.project_lluc.bank_back_lluc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_lluc.bank_back_lluc.model.accounts.Checking;
import com.project_lluc.bank_back_lluc.model.shared.Money;
import com.project_lluc.bank_back_lluc.service.interfaces.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @Autowired
    private ObjectMapper objectMapper;

    private Checking account;

    @BeforeEach
    void setUp() {
        account = new Checking();
        account.setId(1L);
        account.setBalance(new Money(new BigDecimal("1000.00")));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetAccountBalance_AsAdmin() throws Exception {
        Money balance = account.getBalance();
        Mockito.when(adminService.getAccountBalance(eq(1L))).thenReturn(balance);

        mockMvc.perform(get("/api/admin/accounts/1/balance"))
                .andExpect(status().isOk())
                .andExpect(content().string(balance.getAmount().toString()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testUpdateAccountBalance_AsAdmin() throws Exception {
        Money newBalance = new Money(new BigDecimal("2000.00"));
        // we don't stub updateBalance since it returns void; stub getAccountBalance after update
        Mockito.doNothing().when(adminService).updateBalance(eq(1L), eq(newBalance.getAmount().toString()));
        Mockito.when(adminService.getAccountBalance(eq(1L))).thenReturn(newBalance);

        mockMvc.perform(patch("/api/admin/accounts/1/balance")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBalance)))
                .andExpect(status().isOk())
                .andExpect(content().string(newBalance.getAmount().toString()));
    }

    @Test
    void testUnauthorizedAccess() throws Exception {
        mockMvc.perform(get("/api/admin/accounts/1/balance"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteAccount_AsAdmin() throws Exception {
        Mockito.doNothing().when(adminService).deleteAccount(eq(1L));

        mockMvc.perform(delete("/api/admin/accounts/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());

        Mockito.verify(adminService).deleteAccount(1L);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testUpdateAccountStatus_AsAdmin() throws Exception {
        Mockito.doNothing().when(adminService).updateAccountStatus(eq(1L), eq("FROZEN"));

        mockMvc.perform(patch("/api/admin/accounts/1/status")
                        .param("status", "FROZEN")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("Status updated to FROZEN"));

        Mockito.verify(adminService).updateAccountStatus(1L, "FROZEN");
    }
}
