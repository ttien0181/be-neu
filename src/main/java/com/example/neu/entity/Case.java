package com.example.neu.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * JPA entity representing the `cases` table
 */
@Entity
@Table(name = "cases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Case {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_cases_category"))
    private Category category;

    @Column(name = "case_name")
    private String caseName;

    @Column(name = "case_description", columnDefinition = "TEXT")
    private String caseDescription;

    private String status;

    @Column(name = "court_name")
    private String courtName;

    private String location;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}