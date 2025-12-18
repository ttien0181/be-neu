package com.example.neu.exception;

public class QuestionTooFrequentException extends RuntimeException {
    public QuestionTooFrequentException() {
        super("Bạn đã đặt câu hỏi trong vòng 1 ngày. Vui lòng đợi thêm thời gian trước khi đặt câu hỏi mới.");
    }
}

