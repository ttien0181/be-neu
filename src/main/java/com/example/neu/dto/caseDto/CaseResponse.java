package com.example.neu.dto.caseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}