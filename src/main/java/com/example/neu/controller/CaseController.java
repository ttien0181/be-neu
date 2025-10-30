package com.example.neu.controller;

import com.example.neu.dto.APIResponse;
import com.example.neu.dto.caseDto.CaseRequest;
import com.example.neu.dto.caseDto.CaseResponse;
import com.example.neu.service.CaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cases")
public class CaseController {
    public static final String SUCCESS = "SUCCESS";

    private final CaseService caseService;

    public CaseController(CaseService caseService) {
        this.caseService = caseService;
    }

    @GetMapping
    ResponseEntity<APIResponse<List<CaseResponse>>> getAll() {
        APIResponse<List<CaseResponse>> apiResponse = APIResponse.<List<CaseResponse>>builder()
                .status(SUCCESS)
                .result(caseService.findAllCases())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    ResponseEntity<APIResponse<CaseResponse>> getOne(@PathVariable Long id) {
        APIResponse<CaseResponse> apiResponse = APIResponse.<CaseResponse>builder()
                .status(SUCCESS)
                .result(caseService.getCaseById(id))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    ResponseEntity<APIResponse<CaseResponse>> create(@RequestBody CaseRequest caseRequest) {
        APIResponse<CaseResponse> apiResponse = APIResponse.<CaseResponse>builder()
                .status(SUCCESS)
                .result(caseService.createCase(caseRequest))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    ResponseEntity<APIResponse<CaseResponse>> update(@PathVariable Long id, @RequestBody CaseRequest caseRequest) {
        APIResponse<CaseResponse> apiResponse = APIResponse.<CaseResponse>builder()
                .status(SUCCESS)
                .result(caseService.updateCaseById(id, caseRequest))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        caseService.deleteCaseById(id);
        return ResponseEntity.noContent().build();
    }
}