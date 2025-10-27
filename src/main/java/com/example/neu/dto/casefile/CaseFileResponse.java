package com.example.neu.dto.casefile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseFileResponse {
    private Long id;
    private Long caseId;
    private String fileName;
    private String filePath;
    private String fileType;
    private Long uploadedBy;
}
