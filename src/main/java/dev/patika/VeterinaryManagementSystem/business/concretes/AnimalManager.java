package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAnimalService;
import dev.patika.VeterinaryManagementSystem.core.config.ModelMapper.IModelMapperService;
import dev.patika.VeterinaryManagementSystem.core.exception.NotFoundException;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.core.utilies.Msg;
import dev.patika.VeterinaryManagementSystem.core.utilies.ResultHelper;
import dev.patika.VeterinaryManagementSystem.dao.AnimalRepo;
import dev.patika.VeterinaryManagementSystem.dao.CustomerRepo;
import dev.patika.VeterinaryManagementSystem.dto.request.animal.AnimalSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.animal.AnimalUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.animal.AnimalResponse;
import dev.patika.VeterinaryManagementSystem.entities.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalManager implements IAnimalService {

    private final AnimalRepo animalRepo;
    private final CustomerRepo customerRepo;
    private final IModelMapperService modelMapperService;

    @Autowired
    public AnimalManager(AnimalRepo animalRepo, CustomerRepo customerRepo, IModelMapperService modelMapperService) {
        this.animalRepo = animalRepo;
        this.customerRepo = customerRepo;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public ResultData<AnimalResponse> addAnimal(AnimalSaveRequest request) {
        if (!customerRepo.existsById(request.getCustomerId())) {
            throw new NotFoundException("Customer not found with ID: " + request.getCustomerId());
        }

        Animal animal = modelMapperService.forRequest().map(request, Animal.class);
        Animal savedAnimal = animalRepo.save(animal);
        AnimalResponse response = modelMapperService.forResponse().map(savedAnimal, AnimalResponse.class);
        return ResultHelper.created(response);
    }

    @Override
    public ResultData<AnimalResponse> updateAnimal(long animalId, AnimalUpdateRequest updateRequest) {
        Optional<Animal> optionalAnimal = animalRepo.findById(animalId);
        if (!optionalAnimal.isPresent()) {
            throw new NotFoundException("Animal not found with ID: " + animalId);
        }
        Animal existingAnimal = optionalAnimal.get();

        if (updateRequest.getAnimalName() != null) {
            existingAnimal.setAnimalName(updateRequest.getAnimalName());
        }
        if (updateRequest.getAnimalSpecies() != null) {
            existingAnimal.setAnimalSpecies(updateRequest.getAnimalSpecies());
        }
        if (updateRequest.getAnimalBreed() != null) {
            existingAnimal.setAnimalBreed(updateRequest.getAnimalBreed());
        }
        if (updateRequest.getAnimalGender() != null) {
            existingAnimal.setAnimalGender(updateRequest.getAnimalGender());
        }
        if (updateRequest.getAnimalColour() != null) {
            existingAnimal.setAnimalColour(updateRequest.getAnimalColour());
        }
        if (updateRequest.getAnimalDateOfBirth() != null) {
            existingAnimal.setAnimalDateOfBirth(updateRequest.getAnimalDateOfBirth());
        }

        Animal updatedAnimal = animalRepo.save(existingAnimal);
        AnimalResponse response = modelMapperService.forResponse().map(updatedAnimal, AnimalResponse.class);
        return new ResultData<>(true, Msg.ANIMAL_UPDATED, "200", response);
    }



    @Override
    public List<AnimalResponse> getAllAnimals() {
        List<Animal> animals = animalRepo.findAll();
        List<AnimalResponse> animalResponses = new ArrayList<>();

        for (Animal animal : animals) {
            animalResponses.add(modelMapperService.forResponse().map(animal, AnimalResponse.class));
        }

        return animalResponses;
    }

    @Override
    public AnimalResponse getAnimalById(long animalId) {
        Optional<Animal> optionalAnimal = animalRepo.findById(animalId);
        if (!optionalAnimal.isPresent()) {
            throw new NotFoundException(Msg.ANIMAL_NOT_FOUND + " with ID: " + animalId);
        }
        Animal animal = optionalAnimal.get();
        return modelMapperService.forResponse().map(animal, AnimalResponse.class);
    }


    @Override
    public void deleteAnimalById(long animalId) {
        Optional<Animal> optionalAnimal = animalRepo.findById(animalId);
        if (!optionalAnimal.isPresent()) {
            throw new NotFoundException(Msg.ANIMAL_NOT_FOUND + " with ID: " + animalId);
        }
        animalRepo.delete(optionalAnimal.get());
        // Return success message without throwing exception
        throw new NotFoundException(Msg.ANIMAL_DELETED + " with ID: " + animalId);
    }
}