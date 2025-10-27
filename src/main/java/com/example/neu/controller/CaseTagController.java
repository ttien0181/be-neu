package com.example.neu.controller;

import com.example.neu.dto.APIResponse;
import com.example.neu.dto.casetag.CaseTagRequest;
import com.example.neu.dto.casetag.CaseTagResponse;
import com.example.neu.service.CaseTagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/case-tags")
public class CaseTagController {
    public static final String SUCCESS = "SUCCESS";

    private final CaseTagService caseTagService;

    public CaseTagController(CaseTagService caseTagService) {
        this.caseTagService = caseTagService;
    }

    @GetMapping
    ResponseEntity<APIResponse<List<CaseTagResponse>>> getAll() {
        APIResponse<List<CaseTagResponse>> apiResponse = APIResponse.<List<CaseTagResponse>>builder()
                .status(SUCCESS)
                .result(caseTagService.findAllCaseTags())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    ResponseEntity<APIResponse<CaseTagResponse>> getOne(@PathVariable Long id) {
        APIResponse<CaseTagResponse> apiResponse = APIResponse.<CaseTagResponse>builder()
                .status(SUCCESS)
                .result(caseTagService.getCaseTagById(id))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    ResponseEntity<APIResponse<CaseTagResponse>> create(@RequestBody CaseTagRequest caseTagRequest) {
        APIResponse<CaseTagResponse> apiResponse = APIResponse.<CaseTagResponse>builder()
                .status(SUCCESS)
                .result(caseTagService.createCaseTag(caseTagRequest))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    ResponseEntity<APIResponse<CaseTagResponse>> update(@PathVariable Long id, @RequestBody CaseTagRequest caseTagRequest) {
        APIResponse<CaseTagResponse> apiResponse = APIResponse.<CaseTagResponse>builder()
                .status(SUCCESS)
                .result(caseTagService.updateCaseTagById(id, caseTagRequest))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        caseTagService.deleteCaseTagById(id);
        return ResponseEntity.noContent().build();
    }
}