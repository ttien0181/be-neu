package com.example.neu.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * JPA entity representing the `persons` table
 */
@Entity
@Table(name = "persons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "contact_info")
    private String contactInfo;

    public enum Role {
        plaintiff, defendant, lawyer
    }
}