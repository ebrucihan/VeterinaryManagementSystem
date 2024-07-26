package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.animalvaccine.AnimalVaccineSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.animalvaccine.AnimalVaccineUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.animalvaccine.AnimalVaccineResponse;

import java.time.LocalDate;
import java.util.List;

public interface IAnimalVaccineService {

    // Add a new animal vaccine record
    ResultData<AnimalVaccineResponse> addAnimalVaccine(AnimalVaccineSaveRequest request);

    // Update an existing animal vaccine record
    ResultData<AnimalVaccineResponse> updateAnimalVaccine(Long id, AnimalVaccineUpdateRequest request);

    // Retrieve a list of vaccines for a specific animal by its ID
    List<AnimalVaccineResponse> getAnimalVaccinesByAnimalId(Long animalId);

    // Retrieve a list of vaccines that fall within the specified protection period
    List<AnimalVaccineResponse> getAnimalVaccinesByProtectionPeriod(LocalDate startDate, LocalDate endDate);

    // Delete an animal vaccine record by its ID
    ResultData<String> deleteAnimalVaccine(Long id);
}
