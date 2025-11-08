package com.example.neu.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_questioner", nullable = false)
    private User questioner;

    @ManyToOne
    @JoinColumn(name = "id_lawyer")
    private Person lawyer;

    @ManyToOne
    @JoinColumn(name = "case_id")
    private Case caseEntity;

    @Column(length = 500, nullable = false)
    private String content;

    @Column(columnDefinition = "TEXT")
    private String answer;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
