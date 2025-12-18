package com.example.neu.exception;

public class AppointmentPendingException extends RuntimeException {
    public AppointmentPendingException() {
        super("Bạn đã có lịch hẹn đang chờ xử lý với luật sư này. Vui lòng đợi phản hồi hoặc hủy lịch hẹn cũ trước khi đặt lịch mới.");
    }
}

