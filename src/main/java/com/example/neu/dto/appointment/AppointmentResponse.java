package com.example.neu.dto.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentResponse {
    private Long id;
    private String userName;
    private String lawyerName;
    private String lawyerRole;
    private String lawyerEmail;
    // ✅ Cho phép parse từ FE dạng "2025-12-12T06:06"
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private String appointmentTime;
    private String notes;
    private String status; // Trạng thái (PENDING, ACCEPTED, REJECTED)
}