package com.example.neu.repository;

import com.example.neu.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByQuestionerId(Long questionerId);
    List<Question> findByLawyerId(Long lawyerId);
    List<Question> findByCaseEntityId(Long caseId);
}
