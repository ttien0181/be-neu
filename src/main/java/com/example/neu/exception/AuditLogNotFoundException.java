package com.example.neu.exception;

public class AuditLogNotFoundException extends RuntimeException {
    public AuditLogNotFoundException(Long id) {
        super("AuditLog not found with id: " + id);
    }
}