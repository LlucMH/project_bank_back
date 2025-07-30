package com.project_lluc.bank_back_lluc.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new TestExceptionController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void whenResourceNotFound_thenReturns404() throws Exception {
        mockMvc.perform(get("/test/not-found")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Ahora en inglés:
                .andExpect(jsonPath("$.message").value("Resource not found"));
    }

    @Test
    void whenInsufficientFunds_thenReturns400() throws Exception {
        mockMvc.perform(get("/test/insufficient")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Ahora en inglés:
                .andExpect(jsonPath("$.message").value("Insufficient funds"));
    }

    @Test
    void whenUnhandledException_thenReturns500() throws Exception {
        mockMvc.perform(get("/test/generic")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Ahora en inglés:
                .andExpect(jsonPath("$.message").value("An unexpected error has occurred"));
    }

    // — Clase auxiliar anidada estática —
    @RestController
    static class TestExceptionController {
        @GetMapping("/test/not-found")
        public void notFound() {
            throw new ResourceNotFoundException("Resource not found");
        }
        @GetMapping("/test/insufficient")
        public void insufficient() {
            throw new InsufficientFundsException("Insufficient funds");
        }
        @GetMapping("/test/generic")
        public void generic() {
            throw new RuntimeException("generic error");
        }
    }
}
