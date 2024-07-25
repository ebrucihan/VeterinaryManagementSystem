package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IVaccineService;
import dev.patika.VeterinaryManagementSystem.core.exception.NotFoundException;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.core.utilies.Msg;
import dev.patika.VeterinaryManagementSystem.core.utilies.ResultHelper;
import dev.patika.VeterinaryManagementSystem.dao.VaccineRepo;
import dev.patika.VeterinaryManagementSystem.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.vaccine.VaccineResponse;
import dev.patika.VeterinaryManagementSystem.entities.Vaccine;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VaccineManager implements IVaccineService {

    private final VaccineRepo vaccineRepo;

    public VaccineManager(VaccineRepo vaccineRepo) {
        this.vaccineRepo = vaccineRepo;
    }

    @Override
    public ResultData<VaccineResponse> addVaccine(VaccineSaveRequest request) {
        Vaccine vaccine = new Vaccine();
        vaccine.setVaccineName(request.getVaccineName());
        vaccine.setVaccineCode(request.getVaccineCode());
        vaccine.setProtectionStartDate(request.getProtectionStartDate());
        vaccine.setProtectionFinishDate(request.getProtectionFinishDate());

        Vaccine savedVaccine = vaccineRepo.save(vaccine);
        VaccineResponse response = mapToResponse(savedVaccine);
        return ResultHelper.created(response, Msg.VACCINE_CREATED);
    }

    @Override
    public ResultData<VaccineResponse> updateVaccine(Long id, VaccineUpdateRequest request) {
        Vaccine vaccine = vaccineRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.VACCINE_NOT_FOUND));

        vaccine.setVaccineName(request.getVaccineName());
        vaccine.setVaccineCode(request.getVaccineCode());
        vaccine.setProtectionStartDate(request.getProtectionStartDate());
        vaccine.setProtectionFinishDate(request.getProtectionFinishDate());

        Vaccine updatedVaccine = vaccineRepo.save(vaccine);
        VaccineResponse response = mapToResponse(updatedVaccine);
        return ResultHelper.ok(response);
    }

    @Override
    public ResultData<VaccineResponse> getVaccineById(Long id) {
        Vaccine vaccine = vaccineRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.VACCINE_NOT_FOUND));

        VaccineResponse response = mapToResponse(vaccine);
        return ResultHelper.ok(response);
    }

    @Override
    public ResultData<String> deleteVaccine(Long id) {
        Vaccine vaccine = vaccineRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.VACCINE_NOT_FOUND));

        vaccineRepo.delete(vaccine);
        return ResultHelper.ok(Msg.VACCINE_DELETED);
    }

    @Override
    public ResultData<List<VaccineResponse>> getAllVaccines() {
        List<Vaccine> vaccines = vaccineRepo.findAll();
        List<VaccineResponse> responses = vaccines.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResultHelper.ok(responses);
    }

    private VaccineResponse mapToResponse(Vaccine vaccine) {
        VaccineResponse response = new VaccineResponse();
        response.setVaccineId(vaccine.getVaccineId());
        response.setVaccineName(vaccine.getVaccineName());
        response.setVaccineCode(vaccine.getVaccineCode());
        response.setProtectionStartDate(vaccine.getProtectionStartDate());
        response.setProtectionFinishDate(vaccine.getProtectionFinishDate());
        return response;
    }
}