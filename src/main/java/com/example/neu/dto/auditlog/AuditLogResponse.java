package com.example.neu.dto.auditlog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogResponse {
    private Long id;
    private Long userId;
    private String action;
    private Long caseId;
    private Long fileId;
    private String createdAt;
}