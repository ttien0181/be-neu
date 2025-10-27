package com.example.neu.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * JPA entity representing the `audit_logs` table
 */
@Entity
@Table(name = "audit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_audit_user"))
    private User user;

    private String action;

    @ManyToOne
    @JoinColumn(name = "case_id", foreignKey = @ForeignKey(name = "fk_audit_case"))
    private Case caseEntity;

    @ManyToOne
    @JoinColumn(name = "file_id", foreignKey = @ForeignKey(name = "fk_audit_file"))
    private CaseFile file;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}