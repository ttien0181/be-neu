package com.example.neu.controller;

import com.example.neu.dto.APIResponse;
import com.example.neu.dto.question.*;
import com.example.neu.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    public static final String SUCCESS = "SUCCESS";

    @PostMapping
    public ResponseEntity<APIResponse<QuestionResponse>> create(@RequestBody QuestionRequest request) {
        APIResponse<QuestionResponse> res = APIResponse.<QuestionResponse>builder()
                .status(SUCCESS)
                .result(questionService.createQuestion(request))
                .build();
        return ResponseEntity.ok(res);
    }


    @PutMapping("/{id}/answer")
    public ResponseEntity<APIResponse<QuestionResponse>> answer(
            @PathVariable Long id,
            @RequestParam String answer) {
        APIResponse<QuestionResponse> res = APIResponse.<QuestionResponse>builder()
                .status(SUCCESS)
                .result(questionService.answerQuestion(id, answer))
                .build();
        return ResponseEntity.ok(res);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<QuestionResponse>>> getAll() {
        APIResponse<List<QuestionResponse>> res = APIResponse.<List<QuestionResponse>>builder()
                .status(SUCCESS)
                .result(questionService.getAllQuestions())
                .build();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/case/{caseId}")
    public ResponseEntity<APIResponse<List<QuestionResponse>>> getByCase(@PathVariable Long caseId) {
        APIResponse<List<QuestionResponse>> res = APIResponse.<List<QuestionResponse>>builder()
                .status(SUCCESS)
                .result(questionService.getByCase(caseId))
                .build();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<APIResponse<List<QuestionResponse>>> getByUser(@PathVariable Long userId) {
        APIResponse<List<QuestionResponse>> res = APIResponse.<List<QuestionResponse>>builder()
                .status(SUCCESS)
                .result(questionService.getByUser(userId))
                .build();
        return ResponseEntity.ok(res);
    }
}
