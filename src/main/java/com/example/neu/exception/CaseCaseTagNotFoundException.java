package com.example.neu.exception;

public class CaseCaseTagNotFoundException extends RuntimeException {
    public CaseCaseTagNotFoundException(Long caseId, Long caseTagId) {
        super("CaseCaseTag not found with caseId: " + caseId + " and caseTagId: " + caseTagId);
    }
}