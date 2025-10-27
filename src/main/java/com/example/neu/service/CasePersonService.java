package com.example.neu.service;

import com.example.neu.dto.caseperson.CasePersonRequest;
import com.example.neu.dto.caseperson.CasePersonResponse;

import java.util.List;

public interface CasePersonService {
    List<CasePersonResponse> findAllCasePersons();
    CasePersonResponse getCasePersonById(Long caseId, Long personId);
    CasePersonResponse createCasePerson(CasePersonRequest casePersonRequest);
    void deleteCasePerson(Long caseId, Long personId);
}