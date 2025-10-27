package com.example.neu.service;

import com.example.neu.dto.person.PersonRequest;
import com.example.neu.dto.person.PersonResponse;

import java.util.List;

public interface PersonService {
    List<PersonResponse> findAllPersons();
    PersonResponse getPersonById(Long id);
    PersonResponse createPerson(PersonRequest personRequest);
    PersonResponse updatePersonById(Long id, PersonRequest personRequest);
    void deletePersonById(Long id);
}