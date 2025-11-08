package com.example.neu.controller;

import com.example.neu.dto.APIResponse;
import com.example.neu.dto.appointment.AppointmentRequest;
import com.example.neu.dto.appointment.AppointmentResponse;
import com.example.neu.dto.auditlog.AuditLogResponse;
import com.example.neu.entity.Appointment;
import com.example.neu.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    public static final String SUCCESS = "SUCCESS";

    @PostMapping
    public ResponseEntity<APIResponse<AppointmentResponse>> create(@RequestBody AppointmentRequest request) {
        AppointmentResponse response = appointmentService.createAppointment(request);
        APIResponse<AppointmentResponse> apiResponse = APIResponse.<AppointmentResponse>builder()
                .status(SUCCESS)
                .result(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<AppointmentResponse>>> getAll() {
        List<AppointmentResponse> responses = appointmentService.getAllAppointments();
        APIResponse<List<AppointmentResponse>> apiResponse = APIResponse.<List<AppointmentResponse>>builder()
                .status(SUCCESS)
                .result(responses)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<AppointmentResponse>> getById(@PathVariable Long id) {
        AppointmentResponse response = appointmentService.getAppointmentById(id);
        APIResponse<AppointmentResponse> apiResponse = APIResponse.<AppointmentResponse>builder()
                .status(SUCCESS)
                .result(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<APIResponse<AppointmentResponse>> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        AppointmentResponse response = appointmentService.updateStatus(id, status);
        APIResponse<AppointmentResponse> apiResponse = APIResponse.<AppointmentResponse>builder()
                .status(SUCCESS)
                .result(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}