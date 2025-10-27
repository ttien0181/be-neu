package com.example.neu.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * JPA entity representing the `case_files` table
 */
@Entity
@Table(name = "case_files")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "case_id", foreignKey = @ForeignKey(name = "fk_case_file_case"))
    private Case caseEntity;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_type")
    private String fileType;

    @ManyToOne
    @JoinColumn(name = "uploaded_by", foreignKey = @ForeignKey(name = "fk_case_file_user"))
    private User uploadedBy;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}