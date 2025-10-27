package com.example.neu.entity;

import lombok.*;
import java.io.Serializable;

/**
 * Composite key for the `case_case_tags` table
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseCaseTagId implements Serializable {

    private Long caseEntity;
    private Long tag;
}