package com.example.neu.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * JPA entity representing the `case_case_tags` table
 */
@Entity
@Table(name = "case_case_tags")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CaseCaseTagId.class)
public class CaseCaseTag {

    @Id
    @ManyToOne
    @JoinColumn(name = "case_id", foreignKey = @ForeignKey(name = "fk_case_tag_case"))
    private Case caseEntity;

    @Id
    @ManyToOne
    @JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "fk_case_tag_tag"))
    private CaseTag tag;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public CaseCaseTag(Case caseEntity, CaseTag tag) {
        this.caseEntity = caseEntity;
        this.tag = tag;
        this.createdAt = LocalDateTime.now();
    }
}