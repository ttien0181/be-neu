package com.example.neu.dto.question;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionRequest {
    private Long idQuestioner; // User ID (người hỏi)
    private Long idLawyerPerson; // Person ID (luật sư)
    private Long caseId;
    private String content;
    private String answer;
}
