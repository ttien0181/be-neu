package com.example.neu.service;

import com.example.neu.dto.auditlog.AuditLogRequest;
import com.example.neu.dto.auditlog.AuditLogResponse;

import java.util.List;

public interface AuditLogService {
    List<AuditLogResponse> findAllAuditLogs();
    AuditLogResponse getAuditLogById(Long id);
    AuditLogResponse createAuditLog(AuditLogRequest auditLogRequest);
}