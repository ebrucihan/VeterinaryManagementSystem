package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.availabledate.AvailableDateSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.availabledate.AvailableDateUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.availabledate.AvailableDateResponse;

import java.time.LocalDate;
import java.util.List;

public interface IAvailableDateService {

    // Add a new available date for a doctor
    ResultData<AvailableDateResponse> addAvailableDate(AvailableDateSaveRequest request);

    // Retrieve a list of available dates for a specific doctor
    ResultData<List<AvailableDateResponse>> getAvailableDatesByDoctor(Long doctorId);

    // Check if a doctor is available on a specific date
    ResultData<Boolean> isDoctorAvailable(long doctorId, LocalDate date);

    // Retrieve a list of all available dates
    ResultData<List<AvailableDateResponse>> getAllAvailableDates();

    // Update an existing available date
    ResultData<AvailableDateResponse> updateAvailableDate(AvailableDateUpdateRequest request);

    // Delete an available date by its ID
    ResultData<String> deleteAvailableDate(Long id);
}
