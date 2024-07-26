package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.vaccine.VaccineResponse;

import java.util.List;

public interface IVaccineService {

    // Add a new vaccine
    ResultData<VaccineResponse> addVaccine(VaccineSaveRequest request);

    // Update an existing vaccine by its ID
    ResultData<VaccineResponse> updateVaccine(Long id, VaccineUpdateRequest request);

    // Retrieve a specific vaccine by its ID
    ResultData<VaccineResponse> getVaccineById(Long id);

    // Delete a vaccine by its ID
    ResultData<String> deleteVaccine(Long id);

    // Retrieve a list of all vaccines
    ResultData<List<VaccineResponse>> getAllVaccines();
}
