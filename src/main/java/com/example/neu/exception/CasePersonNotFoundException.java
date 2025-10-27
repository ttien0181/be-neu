package com.example.neu.exception;

public class CasePersonNotFoundException extends RuntimeException {
    public CasePersonNotFoundException(Long caseId, Long personId) {
        super("CasePerson not found with caseId: " + caseId + " and personId: " + personId);
    }
}