package com.example.neu.exception;

public class CaseTagNotFoundException extends RuntimeException {
    public CaseTagNotFoundException(Long id) {
        super("CaseTag not found with id: " + id);
    }
}