package com.example.neu.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * JPA entity representing the `case_tags` table
 */
@Entity
@Table(name = "case_tags")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tag_name")
    private String tagName;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}