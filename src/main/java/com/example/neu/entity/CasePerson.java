package com.example.neu.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * JPA entity representing the `case_persons` table
 */
@Entity
@Table(name = "case_persons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CasePersonId.class)
public class CasePerson {

    @Id
    @ManyToOne
    @JoinColumn(name = "case_id", foreignKey = @ForeignKey(name = "fk_case_person_case"))
    private Case caseEntity;

    @Id
    @ManyToOne
    @JoinColumn(name = "person_id", foreignKey = @ForeignKey(name = "fk_case_person_person"))
    private Person person;
}