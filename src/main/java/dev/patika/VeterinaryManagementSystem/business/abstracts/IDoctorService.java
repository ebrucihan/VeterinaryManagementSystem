package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.doctor.DoctorSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.doctor.DoctorUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.doctor.DoctorResponse;

import java.time.LocalDate;
import java.util.List;

public interface IDoctorService {

    // Save a new doctor
    ResultData<DoctorResponse> save(DoctorSaveRequest request);

    // Update an existing doctor by their ID
    ResultData<DoctorResponse> update(long id, DoctorUpdateRequest request);

    // Delete a doctor by their ID
    ResultData<Void> delete(long id);

    // Retrieve a specific doctor by their ID
    ResultData<DoctorResponse> getById(long id);

    // Retrieve a list of all doctors
    ResultData<List<DoctorResponse>> getAll();

    // Check if a doctor is available on a specific date
    ResultData<Boolean> isDoctorAvailable(long doctorId, LocalDate date);
}
