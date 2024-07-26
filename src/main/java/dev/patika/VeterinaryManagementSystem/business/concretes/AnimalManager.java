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

        // Check if an animal with the same name and customerId already exists
        if (animalRepo.existsByAnimalNameAndCustomer_CustomerId(request.getAnimalName(), request.getCustomerId())) {
            throw new IllegalArgumentException("Animal with the same name already exists for this customer.");
        }

        // Create and save the animal entity
        Animal animal = new Animal();
        animal.setAnimalName(request.getAnimalName());
        animal.setAnimalSpecies(request.getAnimalSpecies()); // Set animal species
        animal.setAnimalBreed(request.getAnimalBreed()); // Set animal breed
        animal.setAnimalGender(request.getAnimalGender()); // Set animal gender
        animal.setAnimalColour(request.getAnimalColour()); // Set animal color
        animal.setAnimalDateOfBirth(request.getAnimalDateOfBirth()); // Set animal date of birth

        // Fetch the customer entity and associate it with the animal
        Customer customer = customerRepo.findById(request.getCustomerId()).orElseThrow(() ->
                new NotFoundException("Customer not found with ID: " + request.getCustomerId()));
        animal.setCustomer(customer);

        // Save the animal and create the response
        Animal savedAnimal = animalRepo.save(animal);

        // Create the response manually
        AnimalResponse response = new AnimalResponse();
        response.setAnimalId(savedAnimal.getAnimalId());
        response.setAnimalName(savedAnimal.getAnimalName());
        response.setAnimalSpecies(savedAnimal.getAnimalSpecies()); // Set animal species
        response.setAnimalBreed(savedAnimal.getAnimalBreed()); // Set animal breed
        response.setAnimalGender(savedAnimal.getAnimalGender()); // Set animal gender
        response.setAnimalColour(savedAnimal.getAnimalColour()); // Set animal color
        response.setAnimalDateOfBirth(savedAnimal.getAnimalDateOfBirth()); // Set animal date of birth
        response.setCustomerId(savedAnimal.getCustomer().getCustomerId());

        return ResultHelper.created(response, Msg.ANIMAL_CREATED);
    }

    @Override
    public ResultData<AnimalResponse> updateAnimal(long animalId, AnimalUpdateRequest updateRequest) {
        // Fetch the existing animal entity
        Optional<Animal> optionalAnimal = animalRepo.findById(animalId);
        if (!optionalAnimal.isPresent()) {
            throw new NotFoundException("Animal not found with ID: " + animalId);
        }
        Animal existingAnimal = optionalAnimal.get();

        // Validate that the customer ID cannot be changed
        if (updateRequest.getCustomerId() != null && !updateRequest.getCustomerId().equals(existingAnimal.getCustomer().getCustomerId())) {
            throw new IllegalArgumentException("Customer ID cannot be changed.");
        }

        // Update the animal fields with the new data
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

        // Save the updated animal and create the response
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

        return new ResultData<>(true, Msg.ANIMAL_UPDATED, "200", response);
    }

    @Override
    public List<AnimalResponse> getAllAnimals() {
        // Retrieve all animals from the repository
        List<Animal> animals = animalRepo.findAll();
        return animals.stream()
                .map(animal -> {
                    // Map each animal to an AnimalResponse
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
        // Fetch a specific animal by ID
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
        // Check if the animal exists and delete it
        Optional<Animal> optionalAnimal = animalRepo.findById(animalId);
        if (!optionalAnimal.isPresent()) {
            throw new NotFoundException(Msg.ANIMAL_NOT_FOUND + " with ID: " + animalId);
        }
        animalRepo.delete(optionalAnimal.get());
        return ResultHelper.deleted(Msg.ANIMAL_DELETED + " with ID: " + animalId);
    }

    @Override
    public List<AnimalResponse> getAnimalsByName(String animalName) {
        // Find animals by name
        List<Animal> animals = animalRepo.findByAnimalNameContainingIgnoreCase(animalName);
        if (animals.isEmpty()) {
            throw new NotFoundException(Msg.ANIMAL_NOT_FOUND + " with name containing: " + animalName);
        }
        return animals.stream()
                .map(animal -> {
                    // Map each animal to an AnimalResponse
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
