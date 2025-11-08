package com.example.neu.service;

import com.example.neu.dto.question.QuestionRequest;
import com.example.neu.dto.question.QuestionResponse;
import java.util.List;

public interface QuestionService {
    QuestionResponse createQuestion(QuestionRequest request);
    QuestionResponse answerQuestion(Long id, String answer);
    List<QuestionResponse> getAllQuestions();
    List<QuestionResponse> getByCase(Long caseId);
    List<QuestionResponse> getByUser(Long userId);
}
