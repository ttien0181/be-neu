package com.example.neu.controller;

import com.example.neu.dto.APIResponse;
import com.example.neu.dto.auditlog.AuditLogRequest;
import com.example.neu.dto.auditlog.AuditLogResponse;
import com.example.neu.service.AuditLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditlogs")
public class AuditLogController {
    public static final String SUCCESS = "SUCCESS";

    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping
    ResponseEntity<APIResponse<List<AuditLogResponse>>> getAll() {
        APIResponse<List<AuditLogResponse>> apiResponse = APIResponse.<List<AuditLogResponse>>builder()
                .status(SUCCESS)
                .result(auditLogService.findAllAuditLogs())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    ResponseEntity<APIResponse<AuditLogResponse>> getOne(@PathVariable Long id) {
        APIResponse<AuditLogResponse> apiResponse = APIResponse.<AuditLogResponse>builder()
                .status(SUCCESS)
                .result(auditLogService.getAuditLogById(id))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    ResponseEntity<APIResponse<AuditLogResponse>> create(@RequestBody AuditLogRequest auditLogRequest) {
        APIResponse<AuditLogResponse> apiResponse = APIResponse.<AuditLogResponse>builder()
                .status(SUCCESS)
                .result(auditLogService.createAuditLog(auditLogRequest))
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}