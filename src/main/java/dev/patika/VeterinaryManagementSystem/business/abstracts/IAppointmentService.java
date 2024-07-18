package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.appointment.AppointmentUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.appointment.AppointmentResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentService {

    AppointmentResponse createAppointment(AppointmentSaveRequest request);
    AppointmentResponse updateAppointment(AppointmentUpdateRequest request);
    AppointmentResponse getAppointmentById(Long appointmentId);
    void deleteAppointment(Long appointmentId);
    List<AppointmentResponse> getAppointments(Long doctorId, Long animalId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
