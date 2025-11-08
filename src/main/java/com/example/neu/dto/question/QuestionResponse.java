package com.example.neu.dto.question;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionResponse {
    private Long id;
    private String content;
    private String answer;

    private Long questionerId; // ✅ ID người hỏi
    private String questionerName;

    private Long lawyerId; // ✅ ID luật sư
    private String lawyerName;
    private String lawyerRole;
    private String lawyerEmail;

    private String caseName;
    private String createdAt;
    private String updatedAt;
}
