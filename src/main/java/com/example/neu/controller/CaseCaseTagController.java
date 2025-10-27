package com.example.neu.controller;

import com.example.neu.dto.APIResponse;
import com.example.neu.dto.casecasetag.CaseCaseTagRequest;
import com.example.neu.dto.casecasetag.CaseCaseTagResponse;
import com.example.neu.service.CaseCaseTagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/casecasetags")
public class CaseCaseTagController {
    public static final String SUCCESS = "SUCCESS";

    private final CaseCaseTagService caseCaseTagService;

    public CaseCaseTagController(CaseCaseTagService caseCaseTagService) {
        this.caseCaseTagService = caseCaseTagService;
    }

    @GetMapping
    ResponseEntity<APIResponse<List<CaseCaseTagResponse>>> getAll() {
        APIResponse<List<CaseCaseTagResponse>> apiResponse = APIResponse.<List<CaseCaseTagResponse>>builder()
                .status(SUCCESS)
                .result(caseCaseTagService.findAllCaseCaseTags())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{caseId}/{caseTagId}")
    ResponseEntity<APIResponse<CaseCaseTagResponse>> getOne(@PathVariable Long caseId, @PathVariable Long caseTagId) {
        APIResponse<CaseCaseTagResponse> apiResponse = APIResponse.<CaseCaseTagResponse>builder()
                .status(SUCCESS)
                .result(caseCaseTagService.getCaseCaseTagById(caseId, caseTagId))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    ResponseEntity<APIResponse<CaseCaseTagResponse>> create(@RequestBody CaseCaseTagRequest caseCaseTagRequest) {
        APIResponse<CaseCaseTagResponse> apiResponse = APIResponse.<CaseCaseTagResponse>builder()
                .status(SUCCESS)
                .result(caseCaseTagService.createCaseCaseTag(caseCaseTagRequest))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{caseId}/{caseTagId}")
    ResponseEntity<Void> delete(@PathVariable Long caseId, @PathVariable Long caseTagId) {
        caseCaseTagService.deleteCaseCaseTag(caseId, caseTagId);
        return ResponseEntity.noContent().build();
    }
}