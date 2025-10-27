package com.example.neu.dto.caseDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseRequest {
    @NotBlank
    private String caseName;
    private String caseDescription;
    private String status;
    private String courtName;
    private String location;
    private Long categoryId;
}