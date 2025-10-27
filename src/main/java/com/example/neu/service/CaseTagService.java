package com.example.neu.service;

import com.example.neu.dto.casetag.CaseTagRequest;
import com.example.neu.dto.casetag.CaseTagResponse;

import java.util.List;

public interface CaseTagService {
    List<CaseTagResponse> findAllCaseTags();
    CaseTagResponse getCaseTagById(Long id);
    CaseTagResponse createCaseTag(CaseTagRequest caseTagRequest);
    CaseTagResponse updateCaseTagById(Long id, CaseTagRequest caseTagRequest);
    void deleteCaseTagById(Long id);
}