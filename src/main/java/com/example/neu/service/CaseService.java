package com.example.neu.service;

import com.example.neu.dto.caseDto.CaseRequest;
import com.example.neu.dto.caseDto.CaseResponse;

import java.util.List;

public interface CaseService {
    List<CaseResponse> findAllCases();
    CaseResponse getCaseById(Long id);
    CaseResponse createCase(CaseRequest caseRequest);
    CaseResponse updateCaseById(Long id, CaseRequest caseRequest);
    void deleteCaseById(Long id);
}