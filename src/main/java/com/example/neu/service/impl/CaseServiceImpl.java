package com.example.neu.service.impl;

import com.example.neu.dto.caseDto.CaseRequest;
import com.example.neu.dto.caseDto.CaseResponse;
import com.example.neu.entity.Case;
import com.example.neu.entity.Category;
import com.example.neu.exception.CaseNotFoundException;
import com.example.neu.exception.CategoryNotFoundException;
import com.example.neu.repository.CaseRepository;
import com.example.neu.repository.CategoryRepository;
import com.example.neu.service.CaseService;
import com.example.neu.util.ValueMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CaseServiceImpl implements CaseService {

    private final CaseRepository caseRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<CaseResponse> findAllCases() {
        List<Case> cases = caseRepository.findAll();
        List<CaseResponse> caseResponses = new java.util.ArrayList<>(List.of());
        for(Case caseEntity: cases){
            CaseResponse caseResponse =  ValueMapper.MAPPER.convertToCaseResponse(caseEntity);
            caseResponse.setCategoryId(caseEntity.getCategory().getId());
            caseResponses.add(caseResponse);
        }
        return caseResponses;
    }

    @Override
    public CaseResponse getCaseById(Long id) {
        Case caseEntity = caseRepository.findById(id)
                .orElseThrow(() -> new CaseNotFoundException(id));
        CaseResponse caseResponse =  ValueMapper.MAPPER.convertToCaseResponse(caseEntity);
        caseResponse.setCategoryId(caseEntity.getCategory().getId());
        return caseResponse;
    }

    @Override
    public CaseResponse createCase(CaseRequest caseRequest) {
        Category category = categoryRepository.findById(caseRequest.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(caseRequest.getCategoryId()));

        Case caseEntity = ValueMapper.MAPPER.convertToCase(caseRequest);
        caseEntity.setCategory(category);
        caseEntity = caseRepository.save(caseEntity);
        CaseResponse caseResponse =  ValueMapper.MAPPER.convertToCaseResponse(caseEntity);
        caseResponse.setCategoryId(category.getId());
        return caseResponse;
    }

    @Override
    public CaseResponse updateCaseById(Long id, CaseRequest caseRequest) {
        Case existing = caseRepository.findById(id)
                .orElseThrow(() -> new CaseNotFoundException(id));

        Category category = categoryRepository.findById(caseRequest.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(caseRequest.getCategoryId()));

        existing.setCaseName(caseRequest.getCaseName());
        existing.setCaseDescription(caseRequest.getCaseDescription());
        existing.setStatus(caseRequest.getStatus());
        existing.setCourtName(caseRequest.getCourtName());
        existing.setLocation(caseRequest.getLocation());
        existing.setCategory(category);

        Case saved = caseRepository.save(existing);
        CaseResponse caseResponse =  ValueMapper.MAPPER.convertToCaseResponse(saved);
        System.out.println(caseResponse);
        caseResponse.setCategoryId(category.getId());
        return caseResponse;
    }

    @Override
    public void deleteCaseById(Long id) {
        if (!caseRepository.existsById(id)) {
            throw new CaseNotFoundException(id);
        }
        caseRepository.deleteById(id);
    }
}