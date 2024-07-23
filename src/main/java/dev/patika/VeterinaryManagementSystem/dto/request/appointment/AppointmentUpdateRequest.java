package dev.patika.VeterinaryManagementSystem.dto.request.appointment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentUpdateRequest {

    private Long appointmentId;
    private Long animal;
    private Long doctor;
    private LocalDateTime appointmentDateTime;
}
