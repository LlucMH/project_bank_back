package com.project_lluc.bank_back_lluc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_lluc.bank_back_lluc.dto.ThirdPartyTransactionDTO;
import com.project_lluc.bank_back_lluc.service.interfaces.ThirdPartyService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ThirdPartyController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ThirdPartyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ThirdPartyService thirdPartyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testMissingHashKeyHeaderReturnsBadRequest() throws Exception {
        ThirdPartyTransactionDTO dto = new ThirdPartyTransactionDTO();
        dto.setAmount(new BigDecimal("100.00"));
        dto.setAccountId(1L);
        dto.setSecretKey("secret123");

        mockMvc.perform(post("/third-party/accounts/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSendMoneyToAccountWithValidHashKey() throws Exception {
        ThirdPartyTransactionDTO dto = new ThirdPartyTransactionDTO();
        dto.setAmount(new BigDecimal("100.00"));
        dto.setAccountId(1L);
        dto.setSecretKey("secret123");

        mockMvc.perform(post("/third-party/accounts/send")
                        .header("hashed-key", "VALID_HASHED_KEY")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        Mockito.verify(thirdPartyService)
                .sendMoney("VALID_HASHED_KEY", 1L, "secret123", new BigDecimal("100.00"));
    }

    @Test
    void testReceiveMoneyFromAccountWithValidHashKey() throws Exception {
        ThirdPartyTransactionDTO dto = new ThirdPartyTransactionDTO();
        dto.setAmount(new BigDecimal("50.00"));
        dto.setAccountId(2L);
        dto.setSecretKey("secret456");

        mockMvc.perform(post("/third-party/accounts/receive")
                        .header("hashed-key", "VALID_HASHED_KEY")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        Mockito.verify(thirdPartyService)
                .receiveMoney("VALID_HASHED_KEY", 2L, "secret456", new BigDecimal("50.00"));
    }
}

