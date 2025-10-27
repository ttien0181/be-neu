package com.example.neu.exception;

public class CaseFileNotFoundException extends RuntimeException {
    public CaseFileNotFoundException(Long id) {
        super("CaseFile not found with id: " + id);
    }
}
