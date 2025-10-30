package com.example.neu.service;

import com.example.neu.dto.casefile.CaseFileRequest;
import com.example.neu.dto.casefile.CaseFileResponse;
import org.springframework.core.io.Resource;

import java.util.List;

public interface CaseFileService {
    List<CaseFileResponse> findAllCaseFiles();
    CaseFileResponse getCaseFileById(Long id);
    CaseFileResponse createCaseFile(CaseFileRequest caseFileRequest);
    void deleteCaseFile(Long id);
    byte[] downloadCaseFile(Long caseId, String fileName);
    Resource previewCaseFile(Long caseId, String fileName);

}
