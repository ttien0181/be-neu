package com.example.neu.controller;

import com.example.neu.dto.APIResponse;
import com.example.neu.dto.person.PersonRequest;
import com.example.neu.dto.person.PersonResponse;
import com.example.neu.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
    public static final String SUCCESS = "SUCCESS";

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    ResponseEntity<APIResponse<List<PersonResponse>>> getAll() {
        APIResponse<List<PersonResponse>> apiResponse = APIResponse.<List<PersonResponse>>builder()
                .status(SUCCESS)
                .result(personService.findAllPersons())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    ResponseEntity<APIResponse<PersonResponse>> getOne(@PathVariable Long id) {
        APIResponse<PersonResponse> apiResponse = APIResponse.<PersonResponse>builder()
                .status(SUCCESS)
                .result(personService.getPersonById(id))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    ResponseEntity<APIResponse<PersonResponse>> create(@RequestBody PersonRequest personRequest) {
        APIResponse<PersonResponse> apiResponse = APIResponse.<PersonResponse>builder()
                .status(SUCCESS)
                .result(personService.createPerson(personRequest))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    ResponseEntity<APIResponse<PersonResponse>> update(@PathVariable Long id, @RequestBody PersonRequest personRequest) {
        APIResponse<PersonResponse> apiResponse = APIResponse.<PersonResponse>builder()
                .status(SUCCESS)
                .result(personService.updatePersonById(id, personRequest))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        personService.deletePersonById(id);
        return ResponseEntity.noContent().build();
    }
}