package com.example.neu.service;

import com.example.neu.dto.appointment.AppointmentRequest;
import com.example.neu.dto.appointment.AppointmentResponse;

import java.util.List;

public interface AppointmentService {
    AppointmentResponse createAppointment(AppointmentRequest request);
    List<AppointmentResponse> getAllAppointments();
    AppointmentResponse getAppointmentById(Long id);
    AppointmentResponse updateStatus(Long id, String status);
}