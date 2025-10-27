package com.example.neu.controller;

import com.example.neu.dto.APIResponse;
import com.example.neu.dto.caseperson.CasePersonRequest;
import com.example.neu.dto.caseperson.CasePersonResponse;
import com.example.neu.service.CasePersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/case-persons")
public class CasePersonController {
    public static final String SUCCESS = "SUCCESS";

    private final CasePersonService casePersonService;

    public CasePersonController(CasePersonService casePersonService) {
        this.casePersonService = casePersonService;
    }

    @GetMapping
    ResponseEntity<APIResponse<List<CasePersonResponse>>> getAll() {
        APIResponse<List<CasePersonResponse>> apiResponse = APIResponse.<List<CasePersonResponse>>builder()
                .status(SUCCESS)
                .result(casePersonService.findAllCasePersons())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{caseId}/{personId}")
    ResponseEntity<APIResponse<CasePersonResponse>> getOne(@PathVariable Long caseId, @PathVariable Long personId) {
        APIResponse<CasePersonResponse> apiResponse = APIResponse.<CasePersonResponse>builder()
                .status(SUCCESS)
                .result(casePersonService.getCasePersonById(caseId, personId))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    ResponseEntity<APIResponse<CasePersonResponse>> create(@RequestBody CasePersonRequest casePersonRequest) {
        APIResponse<CasePersonResponse> apiResponse = APIResponse.<CasePersonResponse>builder()
                .status(SUCCESS)
                .result(casePersonService.createCasePerson(casePersonRequest))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{caseId}/{personId}")
    ResponseEntity<Void> delete(@PathVariable Long caseId, @PathVariable Long personId) {
        casePersonService.deleteCasePerson(caseId, personId);
        return ResponseEntity.noContent().build();
    }
}