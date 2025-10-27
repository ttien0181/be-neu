package com.example.neu.exception;

public class CaseNotFoundException extends RuntimeException {
    public CaseNotFoundException(Long id) {
        super("Case not found with id: " + id);
    }
}