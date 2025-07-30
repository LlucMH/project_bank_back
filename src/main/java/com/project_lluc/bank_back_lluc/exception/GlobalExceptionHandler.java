package com.project_lluc.bank_back_lluc.exception;

import com.project_lluc.bank_back_lluc.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ResponseStatus;

// tus excepciones
import com.project_lluc.bank_back_lluc.exception.ResourceNotFoundException;
import com.project_lluc.bank_back_lluc.exception.InsufficientFundsException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNotFound(ResourceNotFoundException ex) {
        ErrorDTO body = new ErrorDTO("NOT_FOUND", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorDTO> handleInsufficient(InsufficientFundsException ex) {
        ErrorDTO body = new ErrorDTO("INSUFFICIENT_FUNDS", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorDTO> handleMissingHeader(MissingRequestHeaderException ex) {
        ErrorDTO body = new ErrorDTO("BAD_REQUEST",
                "Required header '" + ex.getHeaderName() + "' is missing");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleOther(Exception ex) {
        ErrorDTO body = new ErrorDTO("INTERNAL_ERROR", "An unexpected error has occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
