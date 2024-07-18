package dev.patika.VeterinaryManagementSystem.dto.request.appointment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentUpdateRequest {

    private Long appointmentId;
    private Long animalId;
    private Long doctorId;
    private LocalDateTime appointmentDateTime;
}
