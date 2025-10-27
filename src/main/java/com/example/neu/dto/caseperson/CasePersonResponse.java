package com.example.neu.dto.caseperson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CasePersonResponse {
    private Long caseId;
    private Long personId;
}