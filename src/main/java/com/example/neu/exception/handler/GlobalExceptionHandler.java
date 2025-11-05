package com.example.neu.exception.handler;

import com.example.neu.dto.APIResponse;
import com.example.neu.dto.common.ErrorDetail;
import com.example.neu.exception.AuditLogNotFoundException;
import com.example.neu.exception.CategoryNotFoundException;
import com.example.neu.exception.UserNotFoundException;
import com.example.neu.exception.UsernameAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Collections;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String FAILURE = "FAILURE";

    @ExceptionHandler({
            UserNotFoundException.class,
            UsernameAlreadyExistsException.class
    })
    ResponseEntity<APIResponse<ErrorDetail>> handlingUseException(RuntimeException exception){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .field("user")
                .errorMessage(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        APIResponse<ErrorDetail> response = APIResponse.<ErrorDetail>builder()
                .status(FAILURE)
                .errors(Collections.singletonList(errorDetail))
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler({
            AuditLogNotFoundException.class
    })
    ResponseEntity<APIResponse<ErrorDetail>> handlingAuditException(RuntimeException exception){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .field("audit")
                .errorMessage(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        APIResponse<ErrorDetail> response = APIResponse.<ErrorDetail>builder()
                .status(FAILURE)
                .errors(Collections.singletonList(errorDetail))
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}

