package com.project_lluc.bank_back_lluc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_lluc.bank_back_lluc.model.accounts.Checking;
import com.project_lluc.bank_back_lluc.model.shared.Money;
import com.project_lluc.bank_back_lluc.model.users.AccountHolder;
import com.project_lluc.bank_back_lluc.service.interfaces.AccountHolderService;
import com.project_lluc.bank_back_lluc.service.interfaces.UserService;
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
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@WebMvcTest(AccountHolderController.class)
public class AccountHolderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountHolderService accountHolderService;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private Checking account;
    private AccountHolder holder;

    @BeforeEach
    void setUp() {
        account = new Checking();
        account.setId(1L);
        account.setBalance(new Money(new BigDecimal("1000.00")));

        holder = new AccountHolder();
        holder.setUsername("lluc");
    }

    @Test
    @WithMockUser(username = "lluc", roles = {"ACCOUNTHOLDER"})
    void testGetBalanceAuthorized() throws Exception {
        // Stub UserService
        Mockito.when(userService.findByUsername(anyString()))
                .thenReturn(Optional.of(holder));
        // Stub AccountHolderService
        Mockito.when(accountHolderService.getAccountBalance(anyLong(), anyString()))
                .thenReturn(account.getBalance());

        mockMvc.perform(get("/api/account-holder/accounts/1/balance")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("1000.00"));
    }

    @Test
    void testGetBalanceUnauthorized() throws Exception {
        mockMvc.perform(get("/api/account-holder/accounts/1/balance")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
