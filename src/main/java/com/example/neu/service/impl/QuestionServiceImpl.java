package com.example.neu.service.impl;

import com.example.neu.dto.question.*;
import com.example.neu.entity.*;
import com.example.neu.repository.*;
import com.example.neu.util.ValueMapper;
import com.example.neu.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final CaseRepository caseRepository;

    @Override
    public QuestionResponse createQuestion(QuestionRequest request) {
        User questioner = userRepository.findById(request.getIdQuestioner())
                .orElseThrow(() -> new RuntimeException("Người hỏi không tồn tại"));

        Person lawyer = null;
        if (request.getIdLawyerPerson() != null) {
            lawyer = personRepository.findById(request.getIdLawyerPerson())
                    .orElseThrow(() -> new RuntimeException("Luật sư không tồn tại"));
        }

        Case caseEntity = null;
        if (request.getCaseId() != null) {
            caseEntity = caseRepository.findById(request.getCaseId())
                    .orElseThrow(() -> new RuntimeException("Vụ án không tồn tại"));
        }

        Question question = Question.builder()
                .questioner(questioner)
                .lawyer(lawyer)
                .caseEntity(caseEntity)
                .content(request.getContent())
                .answer(request.getAnswer())
                .build();

        questionRepository.save(question);
        return ValueMapper.MAPPER.convertToQuestionResponse(question);
    }

    @Override
    public QuestionResponse answerQuestion(Long id, String answer) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy câu hỏi"));
        question.setAnswer(answer);
        questionRepository.save(question);
        return ValueMapper.MAPPER.convertToQuestionResponse(question);
    }

    @Override
    public List<QuestionResponse> getAllQuestions() {
        return ValueMapper.MAPPER.convertToQuestionResponseList(questionRepository.findAll());
    }

    @Override
    public List<QuestionResponse> getByCase(Long caseId) {
        return ValueMapper.MAPPER.convertToQuestionResponseList(questionRepository.findByCaseEntityId(caseId));
    }

    @Override
    public List<QuestionResponse> getByUser(Long userId) {
        return ValueMapper.MAPPER.convertToQuestionResponseList(questionRepository.findByQuestionerId(userId));
    }
}
