package com.example.neu.repository;

import com.example.neu.entity.CasePerson;
import com.example.neu.entity.CasePersonId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CasePersonRepository extends JpaRepository<CasePerson, CasePersonId> {

}