package com.example.neu.service.impl;

import com.example.neu.dto.casefile.CaseFileRequest;
import com.example.neu.dto.casefile.CaseFileResponse;
import com.example.neu.entity.Case;
import com.example.neu.entity.CaseFile;
import com.example.neu.entity.User;
import com.example.neu.exception.CaseFileNotFoundException;
import com.example.neu.exception.CaseNotFoundException;
import com.example.neu.exception.UserNotFoundException;
import com.example.neu.repository.CaseFileRepository;
import com.example.neu.repository.CaseRepository;
import com.example.neu.repository.UserRepository;
import com.example.neu.service.CaseFileService;
import com.example.neu.util.ValueMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CaseFileServiceImpl implements CaseFileService {

    private final CaseFileRepository caseFileRepository;
    private final CaseRepository caseRepository;
    private final UserRepository userRepository;

    @Override
    public List<CaseFileResponse> findAllCaseFiles() {
        List<CaseFile> caseFiles = caseFileRepository.findAll();
        return ValueMapper.MAPPER.convertToCaseFileResponseList(caseFiles);
    }

    @Override
    public CaseFileResponse getCaseFileById(Long id) {
        CaseFile caseFile = caseFileRepository.findById(id)
                .orElseThrow(() -> new CaseFileNotFoundException(id));
        return ValueMapper.MAPPER.convertToCaseFileResponse(caseFile);
    }

    @Override
    public CaseFileResponse createCaseFile(CaseFileRequest caseFileRequest) {
        Case caseEntity = caseRepository.findById(caseFileRequest.getCaseId())
                .orElseThrow(() -> new CaseNotFoundException(caseFileRequest.getCaseId()));

        // ✅ Lấy username từ JWT context thay vì FE gửi lên
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        CaseFile caseFile = new CaseFile();
        caseFile.setCaseEntity(caseEntity);
        caseFile.setFileName(caseFileRequest.getFileName());
        caseFile.setFilePath(caseFileRequest.getFilePath());
        caseFile.setFileType(caseFileRequest.getFileType());
        caseFile.setUploadedBy(user);

        caseFile = caseFileRepository.save(caseFile);
        return ValueMapper.MAPPER.convertToCaseFileResponse(caseFile);
    }


    @Override
    public void deleteCaseFile(Long id) {
        if (!caseFileRepository.existsById(id)) {
            throw new CaseFileNotFoundException(id);
        }
        caseFileRepository.deleteById(id);
    }


    @Override
    public byte[] downloadCaseFile(Long caseId, String fileName) {
        try {
            Path filePath = Paths.get("uploads/files/" + caseId + "/" + fileName);

            if (!Files.exists(filePath)) {
                throw new CaseFileNotFoundException(caseId);
            }

            return Files.readAllBytes(filePath);

        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + fileName, e);
        }
    }

    @Override
    public Resource previewCaseFile(Long caseId, String fileName) {
        try {
            Path filePath = Paths.get("uploads/files/" + caseId + "/" + fileName);
            if (!Files.exists(filePath)) {
                throw new CaseFileNotFoundException(caseId);
            }
            return new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid file path: " + fileName, e);
        }
    }


}
