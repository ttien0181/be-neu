package com.example.neu.entity;

import lombok.*;
import java.io.Serializable;

/**
 * Composite key for the `case_persons` table
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CasePersonId implements Serializable {

    private Long caseEntity;
    private Long person;
}