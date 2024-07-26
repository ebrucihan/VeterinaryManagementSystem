package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAnimalVaccineService;
import dev.patika.VeterinaryManagementSystem.core.exception.NotFoundException;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.core.utilies.Msg;
import dev.patika.VeterinaryManagementSystem.core.utilies.ResultHelper;
import dev.patika.VeterinaryManagementSystem.dao.AnimalRepo;
import dev.patika.VeterinaryManagementSystem.dao.AnimalVaccineRepo;
import dev.patika.VeterinaryManagementSystem.dao.VaccineRepo;
import dev.patika.VeterinaryManagementSystem.dto.request.animalvaccine.AnimalVaccineSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.animalvaccine.AnimalVaccineUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.animal.AnimalResponse;
import dev.patika.VeterinaryManagementSystem.dto.response.animalvaccine.AnimalVaccineResponse;
import dev.patika.VeterinaryManagementSystem.dto.response.vaccine.VaccineResponse;
import dev.patika.VeterinaryManagementSystem.entities.Animal;
import dev.patika.VeterinaryManagementSystem.entities.AnimalVaccine;
import dev.patika.VeterinaryManagementSystem.entities.Vaccine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalVaccineManager implements IAnimalVaccineService {

    private final AnimalVaccineRepo animalVaccineRepo;
    private final AnimalRepo animalRepo;
    private final VaccineRepo vaccineRepo;

    @Autowired
    public AnimalVaccineManager(AnimalVaccineRepo animalVaccineRepo, AnimalRepo animalRepo, VaccineRepo vaccineRepo) {
        this.animalVaccineRepo = animalVaccineRepo;
        this.animalRepo = animalRepo;
        this.vaccineRepo = vaccineRepo;
    }

    @Override
    public ResultData<AnimalVaccineResponse> addAnimalVaccine(AnimalVaccineSaveRequest request) {
        // Check if the animal exists
        Animal animal = animalRepo.findById(request.getAnimalId())
                .orElseThrow(() -> new NotFoundException("Animal not found with id: " + request.getAnimalId()));

        // Check if the vaccine exists
        Vaccine vaccine = vaccineRepo.findById(request.getVaccineId())
                .orElseThrow(() -> new NotFoundException("Vaccine not found with id: " + request.getVaccineId()));

        // Validate if the vaccine is available for the specified application date
        validateVaccineAvailability(animal, vaccine, request.getApplicationDate());

        // Create a new AnimalVaccine entity
        AnimalVaccine animalVaccine = new AnimalVaccine();
        animalVaccine.setAnimal(animal);
        animalVaccine.setVaccine(vaccine);
        animalVaccine.setApplicationDate(request.getApplicationDate());

        // Save the AnimalVaccine entity
        AnimalVaccine savedAnimalVaccine = animalVaccineRepo.save(animalVaccine);

        // Map the saved entity to a response DTO
        AnimalVaccineResponse response = mapToResponse(savedAnimalVaccine);
        return ResultHelper.created(response, Msg.ANIMAL_VACCINE_CREATED);
    }

    @Override
    public ResultData<AnimalVaccineResponse> updateAnimalVaccine(Long id, AnimalVaccineUpdateRequest request) {
        // Fetch the existing AnimalVaccine entity
        AnimalVaccine animalVaccine = animalVaccineRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Animal vaccine not found with id: " + id));

        // Check if the new vaccine exists
        Vaccine vaccine = vaccineRepo.findById(request.getVaccineId())
                .orElseThrow(() -> new NotFoundException("Vaccine not found with id: " + request.getVaccineId()));

        // Validate vaccine availability only if the vaccine is different
        if (!animalVaccine.getVaccine().equals(vaccine)) {
            validateVaccineAvailability(animalVaccine.getAnimal(), vaccine, request.getApplicationDate());
        }

        // Update the AnimalVaccine entity with new values
        animalVaccine.setVaccine(vaccine);
        animalVaccine.setApplicationDate(request.getApplicationDate());

        // Save the updated AnimalVaccine entity
        AnimalVaccine updatedAnimalVaccine = animalVaccineRepo.save(animalVaccine);

        // Map the updated entity to a response DTO
        AnimalVaccineResponse response = mapToResponse(updatedAnimalVaccine);
        return ResultHelper.ok(response);
    }

    @Override
    public List<AnimalVaccineResponse> getAnimalVaccinesByAnimalId(Long animalId) {
        // Retrieve all AnimalVaccine records for the given animal ID
        List<AnimalVaccine> animalVaccines = animalVaccineRepo.getAnimalVaccinesByAnimal_AnimalId(animalId);

        // Map the list of entities to a list of response DTOs
        return mapToResponseList(animalVaccines);
    }

    @Override
    public List<AnimalVaccineResponse> getAnimalVaccinesByProtectionPeriod(LocalDate startDate, LocalDate endDate) {
        // Retrieve all AnimalVaccine records where vaccine protection period falls within the specified dates
        List<AnimalVaccine> animalVaccines = animalVaccineRepo.findByVaccine_ProtectionFinishDateBetween(startDate, endDate);
        if (animalVaccines.isEmpty()) {
            throw new NotFoundException("No animal vaccines found within the given protection period");
        }

        // Map the list of entities to a list of response DTOs
        return animalVaccines.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public ResultData<String> deleteAnimalVaccine(Long id) {
        // Find the AnimalVaccine entity to delete
        AnimalVaccine animalVaccine = animalVaccineRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Animal vaccine not found with id: " + id));

        // Delete the AnimalVaccine entity
        animalVaccineRepo.delete(animalVaccine);
        return ResultHelper.ok(Msg.ANIMAL_VACCINE_DELETED);
    }

    private AnimalVaccineResponse mapToResponse(AnimalVaccine animalVaccine) {
        // Map the AnimalVaccine entity to the response DTO
        AnimalVaccineResponse response = new AnimalVaccineResponse();
        response.setId(animalVaccine.getId());
        response.setAnimal(mapAnimalToResponse(animalVaccine.getAnimal()));
        response.setVaccine(mapVaccineToResponse(animalVaccine.getVaccine()));
        response.setApplicationDate(animalVaccine.getApplicationDate());
        return response;
    }

    private List<AnimalVaccineResponse> mapToResponseList(List<AnimalVaccine> animalVaccines) {
        // Map a list of AnimalVaccine entities to a list of response DTOs
        return animalVaccines.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private AnimalResponse mapAnimalToResponse(Animal animal) {
        // Map the Animal entity to the AnimalResponse DTO
        if (animal == null) {
            return null;
        }
        AnimalResponse animalResponse = new AnimalResponse();
        animalResponse.setAnimalId(animal.getAnimalId());
        animalResponse.setAnimalName(animal.getAnimalName());
        animalResponse.setAnimalSpecies(animal.getAnimalSpecies());
        animalResponse.setAnimalBreed(animal.getAnimalBreed());
        animalResponse.setAnimalGender(animal.getAnimalGender());
        animalResponse.setAnimalColour(animal.getAnimalColour());
        animalResponse.setAnimalDateOfBirth(animal.getAnimalDateOfBirth());
        if (animal.getCustomer() != null) {
            animalResponse.setCustomerId(animal.getCustomer().getCustomerId());
        }
        return animalResponse;
    }

    private VaccineResponse mapVaccineToResponse(Vaccine vaccine) {
        // Map the Vaccine entity to the VaccineResponse DTO
        if (vaccine == null) {
            return null;
        }
        VaccineResponse vaccineResponse = new VaccineResponse();
        vaccineResponse.setVaccineId(vaccine.getVaccineId());
        vaccineResponse.setVaccineName(vaccine.getVaccineName());
        vaccineResponse.setVaccineCode(vaccine.getVaccineCode());
        vaccineResponse.setProtectionStartDate(vaccine.getProtectionStartDate());
        vaccineResponse.setProtectionFinishDate(vaccine.getProtectionFinishDate());
        return vaccineResponse;
    }

    private void validateVaccineAvailability(Animal animal, Vaccine vaccine, LocalDate applicationDate) {
        // Check if the vaccine can be applied on the given date
        if (applicationDate.isBefore(vaccine.getProtectionStartDate()) || applicationDate.isAfter(vaccine.getProtectionFinishDate())) {
            throw new IllegalArgumentException("The vaccine is not available for application on the given date.");
        }

        // Check if the vaccine has already been applied to the animal
        boolean exists = animalVaccineRepo.existsByAnimalAndVaccine(animal, vaccine);
        if (exists) {
            throw new IllegalArgumentException("Vaccine has already been applied to this animal.");
        }
    }
}
