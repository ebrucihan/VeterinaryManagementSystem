package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.animalvaccine.AnimalVaccineSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.animalvaccine.AnimalVaccineUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.animalvaccine.AnimalVaccineResponse;

import java.time.LocalDate;
import java.util.List;

public interface IAnimalVaccineService {

    ResultData<AnimalVaccineResponse> addAnimalVaccine(AnimalVaccineSaveRequest request);

    ResultData<AnimalVaccineResponse> updateAnimalVaccine(Long id, AnimalVaccineUpdateRequest request);

    List<AnimalVaccineResponse> getAnimalVaccinesByAnimalId(Long animalId);

    List<AnimalVaccineResponse> getAnimalVaccinesByProtectionPeriod(LocalDate startDate, LocalDate endDate);

    ResultData<String> deleteAnimalVaccine(Long id);
}
