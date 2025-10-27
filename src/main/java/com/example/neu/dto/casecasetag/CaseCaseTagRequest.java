package com.example.neu.dto.casecasetag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseCaseTagRequest {
    private Long caseId;
    private Long caseTagId;
}