package com.example.neu.service.impl;

import com.example.neu.dto.casetag.CaseTagRequest;
import com.example.neu.dto.casetag.CaseTagResponse;
import com.example.neu.entity.CaseTag;
import com.example.neu.exception.CaseTagNotFoundException;
import com.example.neu.repository.CaseTagRepository;
import com.example.neu.service.CaseTagService;
import com.example.neu.util.ValueMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CaseTagServiceImpl implements CaseTagService {

    private final CaseTagRepository caseTagRepository;

    @Override
    public List<CaseTagResponse> findAllCaseTags() {
        List<CaseTag> caseTags = caseTagRepository.findAll();
        return ValueMapper.MAPPER.convertToCaseTagResponseList(caseTags);
    }

    @Override
    public CaseTagResponse getCaseTagById(Long id) {
        CaseTag caseTag = caseTagRepository.findById(id)
                .orElseThrow(() -> new CaseTagNotFoundException(id));
        return ValueMapper.MAPPER.convertToCaseTagResponse(caseTag);
    }

    @Override
    public CaseTagResponse createCaseTag(CaseTagRequest caseTagRequest) {
        CaseTag caseTag = ValueMapper.MAPPER.convertToCaseTag(caseTagRequest);
        caseTag = caseTagRepository.save(caseTag);
        return ValueMapper.MAPPER.convertToCaseTagResponse(caseTag);
    }

    @Override
    public CaseTagResponse updateCaseTagById(Long id, CaseTagRequest caseTagRequest) {
        CaseTag existing = caseTagRepository.findById(id)
                .orElseThrow(() -> new CaseTagNotFoundException(id));

        existing.setTagName(caseTagRequest.getTagName());

        CaseTag saved = caseTagRepository.save(existing);
        return ValueMapper.MAPPER.convertToCaseTagResponse(saved);
    }

    @Override
    public void deleteCaseTagById(Long id) {
        if (!caseTagRepository.existsById(id)) {
            throw new CaseTagNotFoundException(id);
        }
        caseTagRepository.deleteById(id);
    }
}