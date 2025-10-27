package com.example.neu.service.impl;

import com.example.neu.dto.caseperson.CasePersonRequest;
import com.example.neu.dto.caseperson.CasePersonResponse;
import com.example.neu.entity.Case;
import com.example.neu.entity.CasePerson;
import com.example.neu.entity.CasePersonId;
import com.example.neu.entity.Person;
import com.example.neu.exception.CaseNotFoundException;
import com.example.neu.exception.CasePersonNotFoundException;
import com.example.neu.exception.PersonNotFoundException;
import com.example.neu.repository.CasePersonRepository;
import com.example.neu.repository.CaseRepository;
import com.example.neu.repository.PersonRepository;
import com.example.neu.service.CasePersonService;
import com.example.neu.util.ValueMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CasePersonServiceImpl implements CasePersonService {

    private final CasePersonRepository casePersonRepository;
    private final CaseRepository caseRepository;
    private final PersonRepository personRepository;

    @Override
    public List<CasePersonResponse> findAllCasePersons() {
        List<CasePerson> casePersons = casePersonRepository.findAll();
        return ValueMapper.MAPPER.convertToCasePersonResponseList(casePersons);
    }

    @Override
    public CasePersonResponse getCasePersonById(Long caseId, Long personId) {
        CasePersonId id = new CasePersonId(caseId, personId);
        CasePerson casePerson = casePersonRepository.findById(id)
                .orElseThrow(() -> new CasePersonNotFoundException(caseId, personId));
        return ValueMapper.MAPPER.convertToCasePersonResponse(casePerson);
    }

    @Override
    public CasePersonResponse createCasePerson(CasePersonRequest casePersonRequest) {
        Case caseEntity = caseRepository.findById(casePersonRequest.getCaseId())
                .orElseThrow(() -> new CaseNotFoundException(casePersonRequest.getCaseId()));

        Person person = personRepository.findById(casePersonRequest.getPersonId())
                .orElseThrow(() -> new PersonNotFoundException(casePersonRequest.getPersonId()));

        CasePerson casePerson = new CasePerson(caseEntity, person);
        casePerson = casePersonRepository.save(casePerson);
        return ValueMapper.MAPPER.convertToCasePersonResponse(casePerson);
    }

    @Override
    public void deleteCasePerson(Long caseId, Long personId) {
        CasePersonId id = new CasePersonId(caseId, personId);
        if (!casePersonRepository.existsById(id)) {
            throw new CasePersonNotFoundException(caseId, personId);
        }
        casePersonRepository.deleteById(id);
    }
}