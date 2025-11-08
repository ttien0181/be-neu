package com.example.neu.repository;

import com.example.neu.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByContactInfoContainingIgnoreCase(String contact);
}
