package com.example.neu.controller;

import com.example.neu.dto.APIResponse;
import com.example.neu.dto.casefile.CaseFileRequest;
import com.example.neu.dto.casefile.CaseFileResponse;
import com.example.neu.service.CaseFileService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;


import java.util.List;

@RestController
@RequestMapping("/api/casefiles")
public class CaseFileController {
    public static final String SUCCESS = "SUCCESS";

    private final CaseFileService caseFileService;

    public CaseFileController(CaseFileService caseFileService) {
        this.caseFileService = caseFileService;
    }

    @GetMapping
    ResponseEntity<APIResponse<List<CaseFileResponse>>> getAll() {
        APIResponse<List<CaseFileResponse>> apiResponse = APIResponse.<List<CaseFileResponse>>builder()
                .status(SUCCESS)
                .result(caseFileService.findAllCaseFiles())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    ResponseEntity<APIResponse<CaseFileResponse>> getOne(@PathVariable Long id) {
        APIResponse<CaseFileResponse> apiResponse = APIResponse.<CaseFileResponse>builder()
                .status(SUCCESS)
                .result(caseFileService.getCaseFileById(id))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

//    @PostMapping
//    ResponseEntity<APIResponse<CaseFileResponse>> create(@RequestBody CaseFileRequest caseFileRequest) {
//        System.out.println("DEBUG >>> Received CaseFileRequest: " + caseFileRequest);
//        APIResponse<CaseFileResponse> apiResponse = APIResponse.<CaseFileResponse>builder()
//                .status(SUCCESS)
//                .result(caseFileService.createCaseFile(caseFileRequest))
//                .build();
//        return ResponseEntity.ok(apiResponse);
//    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE })
    public ResponseEntity<APIResponse<CaseFileResponse>> uploadCaseFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("caseId") Long caseId,
            @RequestParam("fileName") String fileName,
            @RequestParam("filePath") String filePath,
            @RequestParam("fileType") String fileType
    ) throws IOException {

        // 🧾 LOG TEST
        System.out.println("DEBUG >>> Received file: " + file.getOriginalFilename());
        System.out.println("DEBUG >>> caseId=" + caseId + ", fileName=" + fileName);
        // ⚙️ Tạo request object để tái sử dụng service
        CaseFileRequest caseFileRequest = new CaseFileRequest();
        caseFileRequest.setCaseId(caseId);
        caseFileRequest.setFileName(fileName);
        caseFileRequest.setFilePath(filePath);
        caseFileRequest.setFileType(fileType);

        // 🧩 Lưu metadata vào DB
        CaseFileResponse saved = caseFileService.createCaseFile(caseFileRequest);

        // 💾 Lưu file PDF thực tế vào thư mục
        Path uploadDir = Paths.get("uploads/files/" + caseId);
        Files.createDirectories(uploadDir);
        Path targetPath = uploadDir.resolve(fileName);
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        APIResponse<CaseFileResponse> apiResponse = APIResponse.<CaseFileResponse>builder()
                .status("SUCCESS")
                .result(saved)
                .build();

        return ResponseEntity.ok(apiResponse);
    }


    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        caseFileService.deleteCaseFile(id);
        return ResponseEntity.noContent().build();
    }


}
