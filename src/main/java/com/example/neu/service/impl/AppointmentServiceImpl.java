package com.example.neu.service.impl;

import com.example.neu.dto.appointment.AppointmentRequest;
import com.example.neu.dto.appointment.AppointmentResponse;
import com.example.neu.entity.Appointment;
import com.example.neu.entity.Person;
import com.example.neu.entity.User;
import com.example.neu.exception.AppointmentPendingException;
import com.example.neu.exception.UserNotFoundException;
import com.example.neu.repository.AppointmentRepository;
import com.example.neu.repository.PersonRepository;
import com.example.neu.repository.UserRepository;
import com.example.neu.service.AppointmentService;
import com.example.neu.util.ValueMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final PersonRepository personRepository;

    @Override
    public AppointmentResponse createAppointment(AppointmentRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Person lawyer = personRepository.findById(request.getLawyerId())
                .orElseThrow(() -> new UserNotFoundException("Lawyer not found"));

        // Kiểm tra xem user đã có lịch hẹn PENDING với luật sư này chưa
        appointmentRepository.findByUserIdAndLawyerIdAndStatus(
                request.getUserId(),
                request.getLawyerId(),
                Appointment.Status.PENDING
        ).ifPresent(existingAppointment -> {
            throw new AppointmentPendingException();
        });

        Appointment appointment = Appointment.builder()
                .user(user)
                .lawyer(lawyer)
                .appointmentTime(request.getAppointmentTime())
                .notes(request.getNotes())
                .status(Appointment.Status.PENDING) // Default status when creating
                .build();

        if (request.getStatus() != null) {
            appointment.setStatus(Appointment.Status.valueOf(request.getStatus().toUpperCase()));
        }

        appointmentRepository.save(appointment);
        return ValueMapper.MAPPER.convertToAppointmentResponse(appointment);
    }

    @Override
    public List<AppointmentResponse> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(ValueMapper.MAPPER::convertToAppointmentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentResponse getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Appointment not found"));
        return ValueMapper.MAPPER.convertToAppointmentResponse(appointment);
    }

    @Override
    public AppointmentResponse updateStatus(Long id, String status) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Appointment not found"));

        appointment.setStatus(Appointment.Status.valueOf(status.toUpperCase()));
        appointmentRepository.save(appointment);

        return ValueMapper.MAPPER.convertToAppointmentResponse(appointment);
    }
}