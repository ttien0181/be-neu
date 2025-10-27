package com.example.neu.repository;

import com.example.neu.entity.CaseCaseTag;
import com.example.neu.entity.CaseCaseTagId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseCaseTagRepository extends JpaRepository<CaseCaseTag, CaseCaseTagId> {
}