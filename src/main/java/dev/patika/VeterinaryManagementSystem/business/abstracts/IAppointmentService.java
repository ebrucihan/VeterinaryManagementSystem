package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.appointment.AppointmentUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.appointment.AppointmentResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentService {

    // Create a new appointment based on the provided request data
    ResultData<AppointmentResponse> createAppointment(AppointmentSaveRequest request);

    // Retrieve an appointment by its ID
    ResultData<AppointmentResponse> getAppointmentById(Long appointmentId);

    // Delete an appointment by its ID
    ResultData<Void> deleteAppointment(Long appointmentId);

    // Retrieve a list of appointments based on optional filters: doctor, animal, start date/time, and end date/time
    ResultData<List<AppointmentResponse>> getAppointments(Long doctor, Long animal, LocalDateTime startDateTime, LocalDateTime endDateTime);

    // Update an existing appointment with the provided request data
    ResultData<AppointmentResponse> updateAppointment(AppointmentUpdateRequest request);

    // Retrieve a list of appointments for a specific doctor within a date/time range
    ResultData<List<AppointmentResponse>> getAppointmentsByDoctorAndDate(Long doctorId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    // Retrieve a list of appointments for a specific animal within a date/time range
    ResultData<List<AppointmentResponse>> getAppointmentsByAnimalAndDate(Long animalId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
