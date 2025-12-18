package com.example.neu.repository;

import com.example.neu.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByQuestionerId(Long questionerId);
    List<Question> findByLawyerId(Long lawyerId);
    List<Question> findByCaseEntityId(Long caseId);
    
    Optional<Question> findFirstByQuestionerIdOrderByCreatedAtDesc(Long questionerId);
}
