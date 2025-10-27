package com.example.neu.service.impl;

import com.example.neu.dto.casecasetag.CaseCaseTagRequest;
import com.example.neu.dto.casecasetag.CaseCaseTagResponse;
import com.example.neu.entity.Case;
import com.example.neu.entity.CaseCaseTag;
import com.example.neu.entity.CaseCaseTagId;
import com.example.neu.entity.CaseTag;
import com.example.neu.exception.CaseCaseTagNotFoundException;
import com.example.neu.exception.CaseNotFoundException;
import com.example.neu.exception.CaseTagNotFoundException;
import com.example.neu.repository.CaseCaseTagRepository;
import com.example.neu.repository.CaseRepository;
import com.example.neu.repository.CaseTagRepository;
import com.example.neu.service.CaseCaseTagService;
import com.example.neu.util.ValueMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CaseCaseTagServiceImpl implements CaseCaseTagService {

    private final CaseCaseTagRepository caseCaseTagRepository;
    private final CaseRepository caseRepository;
    private final CaseTagRepository caseTagRepository;

    @Override
    public List<CaseCaseTagResponse> findAllCaseCaseTags() {
        List<CaseCaseTag> caseCaseTags = caseCaseTagRepository.findAll();
        return ValueMapper.MAPPER.convertToCaseCaseTagResponseList(caseCaseTags);
    }

    @Override
    public CaseCaseTagResponse getCaseCaseTagById(Long caseId, Long caseTagId) {
        CaseCaseTagId id = new CaseCaseTagId(caseId, caseTagId);
        CaseCaseTag caseCaseTag = caseCaseTagRepository.findById(id)
                .orElseThrow(() -> new CaseCaseTagNotFoundException(caseId, caseTagId));
        return ValueMapper.MAPPER.convertToCaseCaseTagResponse(caseCaseTag);
    }

    @Override
    public CaseCaseTagResponse createCaseCaseTag(CaseCaseTagRequest caseCaseTagRequest) {
        Case caseEntity = caseRepository.findById(caseCaseTagRequest.getCaseId())
                .orElseThrow(() -> new CaseNotFoundException(caseCaseTagRequest.getCaseId()));

        CaseTag caseTag = caseTagRepository.findById(caseCaseTagRequest.getCaseTagId())
                .orElseThrow(() -> new CaseTagNotFoundException(caseCaseTagRequest.getCaseTagId()));

        CaseCaseTag caseCaseTag = new CaseCaseTag(caseEntity, caseTag);
        caseCaseTag = caseCaseTagRepository.save(caseCaseTag);
        return ValueMapper.MAPPER.convertToCaseCaseTagResponse(caseCaseTag);
    }

    @Override
    public void deleteCaseCaseTag(Long caseId, Long caseTagId) {
        CaseCaseTagId id = new CaseCaseTagId(caseId, caseTagId);
        if (!caseCaseTagRepository.existsById(id)) {
            throw new CaseCaseTagNotFoundException(caseId, caseTagId);
        }
        caseCaseTagRepository.deleteById(id);
    }
}