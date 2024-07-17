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
        Animal animal = animalRepo.findById(request.getAnimalId())
                .orElseThrow(() -> new NotFoundException("Animal not found with id: " + request.getAnimalId()));

        Vaccine vaccine = vaccineRepo.findById(request.getVaccineId())
                .orElseThrow(() -> new NotFoundException("Vaccine not found with id: " + request.getVaccineId()));

        validateVaccineAvailability(animal, vaccine);

        AnimalVaccine animalVaccine = new AnimalVaccine();
        animalVaccine.setAnimal(animal);
        animalVaccine.setVaccine(vaccine);
        animalVaccine.setApplicationDate(request.getApplicationDate());

        AnimalVaccine savedAnimalVaccine = animalVaccineRepo.save(animalVaccine);
        AnimalVaccineResponse response = mapToResponse(savedAnimalVaccine);
        return ResultHelper.created(response, "Animal vaccine created successfully");
    }

    @Override
    public ResultData<AnimalVaccineResponse> updateAnimalVaccine(Long id, AnimalVaccineUpdateRequest request) {
        AnimalVaccine animalVaccine = animalVaccineRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Animal vaccine not found with id: " + id));

        validateVaccineAvailability(animalVaccine.getAnimal(), request.getVaccine());

        animalVaccine.setVaccine(request.getVaccine());
        animalVaccine.setApplicationDate(request.getApplicationDate());

        AnimalVaccine updatedAnimalVaccine = animalVaccineRepo.save(animalVaccine);
        AnimalVaccineResponse response = mapToResponse(updatedAnimalVaccine);
        return ResultHelper.ok(response);
    }

    @Override
    public List<AnimalVaccineResponse> getAnimalVaccinesByAnimalId(Long animalId) {
        List<AnimalVaccine> animalVaccines = animalVaccineRepo.getAnimalVaccinesByAnimal_AnimalId(animalId);
        return mapToResponseList(animalVaccines);
    }

    @Override
    public List<AnimalVaccineResponse> getAnimalVaccinesByProtectionPeriod(LocalDate startDate, LocalDate endDate) {
        List<AnimalVaccine> animalVaccines = animalVaccineRepo.findByVaccine_ProtectionFinishDateBetween(startDate, endDate);
        if (animalVaccines.isEmpty()) {
            throw new NotFoundException("No animal vaccines found within the given protection period");
        }
        return animalVaccines.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public ResultData<String> deleteAnimalVaccine(Long id) {
        AnimalVaccine animalVaccine = animalVaccineRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Animal vaccine not found with id: " + id));

        animalVaccineRepo.delete(animalVaccine);
        return ResultHelper.ok("Animal vaccine deleted successfully");
    }

    private AnimalVaccineResponse mapToResponse(AnimalVaccine animalVaccine) {
        AnimalVaccineResponse response = new AnimalVaccineResponse();
        response.setId(animalVaccine.getId());
        response.setAnimal(mapAnimalToResponse(animalVaccine.getAnimal()));
        response.setVaccine(mapVaccineToResponse(animalVaccine.getVaccine()));
        response.setApplicationDate(animalVaccine.getApplicationDate());
        return response;
    }

    private List<AnimalVaccineResponse> mapToResponseList(List<AnimalVaccine> animalVaccines) {
        return animalVaccines.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private AnimalResponse mapAnimalToResponse(Animal animal) {
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
        // Map customer details
        if (animal.getCustomer() != null) {
            animalResponse.setCustomerId(animal.getCustomer().getCustomerId());
        }
        return animalResponse;
    }

    private VaccineResponse mapVaccineToResponse(Vaccine vaccine) {
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

    private void validateVaccineAvailability(Animal animal, Vaccine vaccine) {
        if (animalVaccineRepo.existsByAnimalAndVaccine(animal, vaccine)) {
            throw new IllegalArgumentException("Vaccine has already been applied to this animal");
        }
    }
}


