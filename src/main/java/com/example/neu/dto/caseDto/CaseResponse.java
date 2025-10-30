package com.example.neu.dto.caseDto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseResponse {
    private Long id;
    private String caseName;
    private String caseDescription;
    private String status;
    private String courtName;
    private String location;
    private Long categoryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}