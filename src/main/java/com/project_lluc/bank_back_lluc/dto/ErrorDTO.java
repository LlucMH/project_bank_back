package com.project_lluc.bank_back_lluc.dto;

import java.time.Instant;

public record ErrorDTO(String code, String message, Instant timestamp) {
    public ErrorDTO(String code, String message) {
        this(code, message, Instant.now());
    }
}
