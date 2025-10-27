package com.example.neu.service;

import com.example.neu.dto.casecasetag.CaseCaseTagRequest;
import com.example.neu.dto.casecasetag.CaseCaseTagResponse;

import java.util.List;

public interface CaseCaseTagService {
    List<CaseCaseTagResponse> findAllCaseCaseTags();
    CaseCaseTagResponse getCaseCaseTagById(Long caseId, Long caseTagId);
    CaseCaseTagResponse createCaseCaseTag(CaseCaseTagRequest caseCaseTagRequest);
    void deleteCaseCaseTag(Long caseId, Long caseTagId);
}