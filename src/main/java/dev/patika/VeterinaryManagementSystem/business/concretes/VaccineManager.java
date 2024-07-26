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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VaccineManager implements IVaccineService {

    private final VaccineRepo vaccineRepo;

    public VaccineManager(VaccineRepo vaccineRepo) {
        this.vaccineRepo = vaccineRepo;
    }

    @Override
    public ResultData<VaccineResponse> addVaccine(VaccineSaveRequest request) {
        // Check if the protection finish date is in the past
        if (request.getProtectionFinishDate().isBefore(LocalDate.now())) {
            return ResultHelper.protectionPeriodNotFinishedError("The protection period of the vaccine has expired.");
        }

        // Check if there is already a vaccine with the same name and valid protection period
        Optional<Vaccine> existingVaccine = vaccineRepo.findByVaccineNameAndProtectionFinishDateAfter(request.getVaccineName(), LocalDate.now());
        if (existingVaccine.isPresent()) {
            return ResultHelper.protectionPeriodNotFinishedError("This vaccine has already been registered and its protection period has not yet finished.");
        }

        // Create a new Vaccine entity and set its properties from the request
        Vaccine newVaccine = new Vaccine();
        newVaccine.setVaccineName(request.getVaccineName());
        newVaccine.setVaccineCode(request.getVaccineCode());
        newVaccine.setProtectionStartDate(request.getProtectionStartDate());
        newVaccine.setProtectionFinishDate(request.getProtectionFinishDate());

        // Save the new Vaccine entity to the database
        Vaccine savedVaccine = vaccineRepo.save(newVaccine);

        // Create a VaccineResponse DTO from the saved Vaccine entity
        VaccineResponse response = new VaccineResponse(
                savedVaccine.getVaccineId(),
                savedVaccine.getVaccineName(),
                savedVaccine.getVaccineCode(),
                savedVaccine.getProtectionStartDate(),
                savedVaccine.getProtectionFinishDate()
        );

        // Return a successful result with the created VaccineResponse
        return ResultHelper.created(response, "Vaccine created successfully");
    }

    @Override
    public ResultData<VaccineResponse> updateVaccine(Long id, VaccineUpdateRequest request) {
        // Find the existing Vaccine entity by ID
        Vaccine vaccine = vaccineRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.VACCINE_NOT_FOUND));

        // Check if the protection finish date is in the past
        if (request.getProtectionFinishDate().isBefore(LocalDate.now())) {
            return ResultHelper.protectionPeriodNotFinishedError("The protection finish date cannot be in the past.");
        }

        // Check if there is already a vaccine with the same name and valid protection period
        Optional<Vaccine> existingVaccine = vaccineRepo.findByVaccineNameAndProtectionFinishDateAfter(request.getVaccineName(), LocalDate.now());
        if (existingVaccine.isPresent() && !existingVaccine.get().getVaccineId().equals(id)) {
            return ResultHelper.protectionPeriodNotFinishedError("This vaccine has already been registered and its protection period has not yet finished.");
        }

        // Update the properties of the existing Vaccine entity from the request
        vaccine.setVaccineName(request.getVaccineName());
        vaccine.setVaccineCode(request.getVaccineCode());
        vaccine.setProtectionStartDate(request.getProtectionStartDate());
        vaccine.setProtectionFinishDate(request.getProtectionFinishDate());

        // Save the updated Vaccine entity to the database
        Vaccine updatedVaccine = vaccineRepo.save(vaccine);

        // Create a VaccineResponse DTO from the updated Vaccine entity
        VaccineResponse response = mapToResponse(updatedVaccine);

        // Return a successful result with the updated VaccineResponse
        return ResultHelper.ok(response);
    }

    @Override
    public ResultData<VaccineResponse> getVaccineById(Long id) {
        // Find the Vaccine entity by ID
        Vaccine vaccine = vaccineRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.VACCINE_NOT_FOUND));

        // Create a VaccineResponse DTO from the found Vaccine entity
        VaccineResponse response = mapToResponse(vaccine);

        // Return a successful result with the VaccineResponse
        return ResultHelper.ok(response);
    }

    @Override
    public ResultData<String> deleteVaccine(Long id) {
        // Find the Vaccine entity by ID
        Vaccine vaccine = vaccineRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.VACCINE_NOT_FOUND));

        // Delete the Vaccine entity from the database
        vaccineRepo.delete(vaccine);

        // Return a successful result with a message indicating the Vaccine was deleted
        return ResultHelper.ok(Msg.VACCINE_DELETED);
    }

    @Override
    public ResultData<List<VaccineResponse>> getAllVaccines() {
        // Retrieve all Vaccine entities from the database
        List<Vaccine> vaccines = vaccineRepo.findAll();

        // Create a list of VaccineResponse DTOs from the Vaccine entities
        List<VaccineResponse> responses = vaccines.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        // Return a successful result with the list of VaccineResponse DTOs
        return ResultHelper.ok(responses);
    }

    private VaccineResponse mapToResponse(Vaccine vaccine) {
        // Create and return a VaccineResponse DTO from the Vaccine entity
        VaccineResponse response = new VaccineResponse();
        response.setVaccineId(vaccine.getVaccineId());
        response.setVaccineName(vaccine.getVaccineName());
        response.setVaccineCode(vaccine.getVaccineCode());
        response.setProtectionStartDate(vaccine.getProtectionStartDate());
        response.setProtectionFinishDate(vaccine.getProtectionFinishDate());
        return response;
    }
}
