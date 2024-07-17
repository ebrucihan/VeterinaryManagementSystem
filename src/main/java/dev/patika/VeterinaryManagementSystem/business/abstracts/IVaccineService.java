package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.vaccine.VaccineResponse;

public interface IVaccineService {

    ResultData<VaccineResponse> addVaccine(VaccineSaveRequest request);
    ResultData<VaccineResponse> updateVaccine(Long id, VaccineUpdateRequest request);
    ResultData<VaccineResponse> getVaccineById(Long id);
    ResultData<String> deleteVaccine(Long id);

}
