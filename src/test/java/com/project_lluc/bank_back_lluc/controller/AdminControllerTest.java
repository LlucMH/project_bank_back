package com.project_lluc.bank_back_lluc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_lluc.bank_back_lluc.model.accounts.Checking;
import com.project_lluc.bank_back_lluc.model.shared.Money;
import com.project_lluc.bank_back_lluc.service.interfaces.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        Mockito.when(adminService.getAccountBalance(1L)).thenReturn(account.getBalance());

        mockMvc.perform(get("/api/admin/accounts/1/balance"))
                .andExpect(status().isOk())
                .andExpect(content().string("1000.00"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testUpdateAccountBalance_AsAdmin() throws Exception {
        Money newBalance = new Money(new BigDecimal("2000.00"));
        Mockito.when(adminService.updateBalance(1L, newBalance)).thenReturn(newBalance);

        mockMvc.perform(patch("/api/admin/accounts/1/balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBalance)))
                .andExpect(status().isOk())
                .andExpect(content().string("2000.00"));
    }

    @Test
    void testUnauthorizedAccess() throws Exception {
        mockMvc.perform(get("/api/admin/accounts/1/balance"))
                .andExpect(status().isUnauthorized());
    }
}
