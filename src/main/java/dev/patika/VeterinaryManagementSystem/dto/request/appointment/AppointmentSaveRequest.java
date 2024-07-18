package dev.patika.VeterinaryManagementSystem.dto.request.appointment;


import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentSaveRequest {

    private Long animal;
    private Long doctor;
    private LocalDateTime appointmentDateTime;
}
