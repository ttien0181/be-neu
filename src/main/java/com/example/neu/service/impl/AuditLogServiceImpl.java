package com.example.neu.service.impl;

import com.example.neu.dto.auditlog.AuditLogRequest;
import com.example.neu.dto.auditlog.AuditLogResponse;
import com.example.neu.entity.AuditLog;
import com.example.neu.entity.Case;
import com.example.neu.entity.CaseFile;
import com.example.neu.entity.User;
import com.example.neu.exception.AuditLogNotFoundException;
import com.example.neu.exception.CaseFileNotFoundException;
import com.example.neu.exception.CaseNotFoundException;
import com.example.neu.exception.UserNotFoundException;
import com.example.neu.repository.AuditLogRepository;
import com.example.neu.repository.CaseFileRepository;
import com.example.neu.repository.CaseRepository;
import com.example.neu.repository.UserRepository;
import com.example.neu.service.AuditLogService;
import com.example.neu.util.ValueMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;
    private final CaseRepository caseRepository;
    private final CaseFileRepository caseFileRepository;

    @Override
    public List<AuditLogResponse> findAllAuditLogs() {
        List<AuditLog> auditLogs = auditLogRepository.findAll();
        return ValueMapper.MAPPER.convertToAuditLogResponseList(auditLogs);
    }

    @Override
    public AuditLogResponse getAuditLogById(Long id) {
        AuditLog auditLog = auditLogRepository.findById(id)
                .orElseThrow(() -> new AuditLogNotFoundException(id));
        return ValueMapper.MAPPER.convertToAuditLogResponse(auditLog);
    }

    @Override
    public AuditLogResponse createAuditLog(AuditLogRequest auditLogRequest) {
        User user = userRepository.findById(auditLogRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException(auditLogRequest.getUserId()));

        Case caseEntity = null;
        if (auditLogRequest.getCaseId() != null) {
            caseEntity = caseRepository.findById(auditLogRequest.getCaseId())
                    .orElseThrow(() -> new CaseNotFoundException(auditLogRequest.getCaseId()));
        }

        CaseFile caseFile = null;
        if (auditLogRequest.getFileId() != null) {
            caseFile = caseFileRepository.findById(auditLogRequest.getFileId())
                    .orElseThrow(() -> new CaseFileNotFoundException(auditLogRequest.getFileId()));
        }

        AuditLog auditLog = new AuditLog();
        auditLog.setUser(user);
        auditLog.setAction(auditLogRequest.getAction());
        auditLog.setCaseEntity(caseEntity);
        auditLog.setFile(caseFile);
        auditLog = auditLogRepository.save(auditLog);
        return ValueMapper.MAPPER.convertToAuditLogResponse(auditLog);
    }
}