package com.example.neu.dto.appointment;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentRequest {
    private Long userId; // Người hẹn
    private Long lawyerId; // Luật sư (người được hẹn)
    private LocalDateTime appointmentTime;
    private String notes;
    private String status; // Trạng thái (PENDING, ACCEPTED, REJECTED)
}