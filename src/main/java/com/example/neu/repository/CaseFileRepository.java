package com.example.neu.repository;

import com.example.neu.entity.CaseFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseFileRepository extends JpaRepository<CaseFile, Long> {
}
