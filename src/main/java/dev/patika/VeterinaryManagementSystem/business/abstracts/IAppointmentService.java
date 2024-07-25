package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.appointment.AppointmentUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.appointment.AppointmentResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentService {
    ResultData<AppointmentResponse> createAppointment(AppointmentSaveRequest request);
    ResultData<AppointmentResponse> getAppointmentById(Long appointmentId);
    ResultData<Void> deleteAppointment(Long appointmentId);
    ResultData<List<AppointmentResponse>> getAppointments(Long doctor, Long animal, LocalDateTime startDateTime);
    ResultData<AppointmentResponse> updateAppointment(AppointmentUpdateRequest request);
    ResultData<List<AppointmentResponse>> getAppointmentsByDoctorAndDate(Long doctorId, LocalDateTime startDateTime);
    ResultData<List<AppointmentResponse>> getAppointmentsByAnimalAndDate(Long animalId, LocalDateTime startDateTime);
}
