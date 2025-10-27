package com.example.neu.dto.casetag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseTagResponse {
    private Long id;
    private String tagName;
}