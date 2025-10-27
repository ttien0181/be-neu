package com.example.neu.service.impl;

import com.example.neu.dto.person.PersonRequest;
import com.example.neu.dto.person.PersonResponse;
import com.example.neu.entity.Person;
import com.example.neu.exception.PersonNotFoundException;
import com.example.neu.repository.PersonRepository;
import com.example.neu.service.PersonService;
import com.example.neu.util.ValueMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public List<PersonResponse> findAllPersons() {
        List<Person> persons = personRepository.findAll();
        return ValueMapper.MAPPER.convertToPersonResponseList(persons);
    }

    @Override
    public PersonResponse getPersonById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
        return ValueMapper.MAPPER.convertToPersonResponse(person);
    }

    @Override
    public PersonResponse createPerson(PersonRequest personRequest) {
        Person person = ValueMapper.MAPPER.convertToPerson(personRequest);
        person = personRepository.save(person);
        return ValueMapper.MAPPER.convertToPersonResponse(person);
    }

    @Override
    public PersonResponse updatePersonById(Long id, PersonRequest personRequest) {
        Person existing = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));

        existing.setName(personRequest.getName());
        existing.setRole(Person.Role.valueOf(personRequest.getRole()));
        existing.setContactInfo(personRequest.getContactInfo());

        Person saved = personRepository.save(existing);
        return ValueMapper.MAPPER.convertToPersonResponse(saved);
    }

    @Override
    public void deletePersonById(Long id) {
        if (!personRepository.existsById(id)) {
            throw new PersonNotFoundException(id);
        }
        personRepository.deleteById(id);
    }
}