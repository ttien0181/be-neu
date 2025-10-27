package com.example.neu.repository;

import com.example.neu.entity.CaseTag;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CaseTagRepository extends JpaRepository<CaseTag, Long> {
    Optional<CaseTag> findByTagName(String tagName);
}