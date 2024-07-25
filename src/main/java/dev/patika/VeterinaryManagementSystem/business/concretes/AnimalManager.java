package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAnimalService;
import dev.patika.VeterinaryManagementSystem.core.config.ModelMapper.IModelMapperService;
import dev.patika.VeterinaryManagementSystem.core.exception.NotFoundException;
import dev.patika.VeterinaryManagementSystem.core.result.Result;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.core.utilies.Msg;
import dev.patika.VeterinaryManagementSystem.core.utilies.ResultHelper;
import dev.patika.VeterinaryManagementSystem.dao.AnimalRepo;
import dev.patika.VeterinaryManagementSystem.dao.CustomerRepo;
import dev.patika.VeterinaryManagementSystem.dto.request.animal.AnimalSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.animal.AnimalUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.animal.AnimalResponse;
import dev.patika.VeterinaryManagementSystem.entities.Animal;
import dev.patika.VeterinaryManagementSystem.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        // Check if the customer exists
        if (!customerRepo.existsById(request.getCustomerId())) {
            throw new NotFoundException("Customer not found with ID: " + request.getCustomerId());
        }

        // Check if animal with the same name and customerId already exists
        if (animalRepo.existsByAnimalNameAndCustomer_CustomerId(request.getAnimalName(), request.getCustomerId())) {
            throw new IllegalArgumentException("Animal with the same name already exists for this customer.");
        }

        // Create and save the animal manually
        Animal animal = new Animal();
        animal.setAnimalName(request.getAnimalName());
        animal.setAnimalSpecies(request.getAnimalSpecies()); // Hayvan türü
        animal.setAnimalBreed(request.getAnimalBreed()); // Hayvan ırkı
        animal.setAnimalGender(request.getAnimalGender()); // Hayvan cinsiyeti
        animal.setAnimalColour(request.getAnimalColour()); // Hayvan rengi
        animal.setAnimalDateOfBirth(request.getAnimalDateOfBirth()); // Hayvan doğum tarihi

        Customer customer = customerRepo.findById(request.getCustomerId()).orElseThrow(() ->
                new NotFoundException("Customer not found with ID: " + request.getCustomerId()));
        animal.setCustomer(customer);

        Animal savedAnimal = animalRepo.save(animal);

        // Create the response manually
        AnimalResponse response = new AnimalResponse();
        response.setAnimalId(savedAnimal.getAnimalId());
        response.setAnimalName(savedAnimal.getAnimalName());
        response.setAnimalSpecies(savedAnimal.getAnimalSpecies()); // Hayvan türü
        response.setAnimalBreed(savedAnimal.getAnimalBreed()); // Hayvan ırkı
        response.setAnimalGender(savedAnimal.getAnimalGender()); // Hayvan cinsiyeti
        response.setAnimalColour(savedAnimal.getAnimalColour()); // Hayvan rengi
        response.setAnimalDateOfBirth(savedAnimal.getAnimalDateOfBirth()); // Hayvan doğum tarihi
        response.setCustomerId(savedAnimal.getCustomer().getCustomerId());

        return ResultHelper.created(response, Msg.ANIMAL_CREATED);
    }

    @Override
    public ResultData<AnimalResponse> updateAnimal(long animalId, AnimalUpdateRequest updateRequest) {
        // Fetch the existing animal
        Optional<Animal> optionalAnimal = animalRepo.findById(animalId);
        if (!optionalAnimal.isPresent()) {
            throw new NotFoundException("Animal not found with ID: " + animalId);
        }
        Animal existingAnimal = optionalAnimal.get();

        // Validate customer ID
        if (updateRequest.getCustomerId() != null && !updateRequest.getCustomerId().equals(existingAnimal.getCustomer().getCustomerId())) {
            throw new IllegalArgumentException("Customer ID cannot be changed.");
        }

        // Update fields manually
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

        // Save the updated animal
        Animal updatedAnimal = animalRepo.save(existingAnimal);

        // Create the response manually
        AnimalResponse response = new AnimalResponse();
        response.setAnimalId(updatedAnimal.getAnimalId());
        response.setAnimalName(updatedAnimal.getAnimalName());
        response.setAnimalSpecies(updatedAnimal.getAnimalSpecies());
        response.setAnimalBreed(updatedAnimal.getAnimalBreed());
        response.setAnimalGender(updatedAnimal.getAnimalGender());
        response.setAnimalColour(updatedAnimal.getAnimalColour());
        response.setAnimalDateOfBirth(updatedAnimal.getAnimalDateOfBirth());
        response.setCustomerId(updatedAnimal.getCustomer().getCustomerId());

        // Return the result
        return new ResultData<>(true, Msg.ANIMAL_UPDATED, "200", response);
    }

    @Override
    public List<AnimalResponse> getAllAnimals() {
        List<Animal> animals = animalRepo.findAll();
        return animals.stream()
                .map(animal -> {
                    AnimalResponse response = new AnimalResponse();
                    response.setAnimalId(animal.getAnimalId());
                    response.setAnimalName(animal.getAnimalName());
                    response.setAnimalSpecies(animal.getAnimalSpecies());
                    response.setAnimalBreed(animal.getAnimalBreed());
                    response.setAnimalGender(animal.getAnimalGender());
                    response.setAnimalColour(animal.getAnimalColour());
                    response.setAnimalDateOfBirth(animal.getAnimalDateOfBirth());
                    response.setCustomerId(animal.getCustomer().getCustomerId());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public AnimalResponse getAnimalById(long animalId) {
        Animal animal = animalRepo.findById(animalId)
                .orElseThrow(() -> new NotFoundException(Msg.ANIMAL_NOT_FOUND + " with ID: " + animalId));
        AnimalResponse response = new AnimalResponse();
        response.setAnimalId(animal.getAnimalId());
        response.setAnimalName(animal.getAnimalName());
        response.setAnimalSpecies(animal.getAnimalSpecies());
        response.setAnimalBreed(animal.getAnimalBreed());
        response.setAnimalGender(animal.getAnimalGender());
        response.setAnimalColour(animal.getAnimalColour());
        response.setAnimalDateOfBirth(animal.getAnimalDateOfBirth());
        response.setCustomerId(animal.getCustomer().getCustomerId());
        return response;
    }

    @Override
    public Result deleteAnimalById(long animalId) {
        Optional<Animal> optionalAnimal = animalRepo.findById(animalId);
        if (!optionalAnimal.isPresent()) {
            throw new NotFoundException(Msg.ANIMAL_NOT_FOUND + " with ID: " + animalId);
        }
        animalRepo.delete(optionalAnimal.get());
        return ResultHelper.deleted(Msg.ANIMAL_DELETED + " with ID: " + animalId);
    }

    @Override
    public List<AnimalResponse> getAnimalsByName(String animalName) {
        List<Animal> animals = animalRepo.findByAnimalNameContainingIgnoreCase(animalName);
        if (animals.isEmpty()) {
            throw new NotFoundException(Msg.ANIMAL_NOT_FOUND + " with name containing: " + animalName);
        }
        return animals.stream()
                .map(animal -> {
                    AnimalResponse response = new AnimalResponse();
                    response.setAnimalId(animal.getAnimalId());
                    response.setAnimalName(animal.getAnimalName());
                    response.setAnimalSpecies(animal.getAnimalSpecies());
                    response.setAnimalBreed(animal.getAnimalBreed());
                    response.setAnimalGender(animal.getAnimalGender());
                    response.setAnimalColour(animal.getAnimalColour());
                    response.setAnimalDateOfBirth(animal.getAnimalDateOfBirth());
                    response.setCustomerId(animal.getCustomer().getCustomerId());
                    return response;
                })
                .collect(Collectors.toList());
    }
}