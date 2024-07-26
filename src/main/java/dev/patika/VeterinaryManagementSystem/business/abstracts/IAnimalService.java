package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.core.result.Result;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.animal.AnimalSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.animal.AnimalUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.animal.AnimalResponse;
import dev.patika.VeterinaryManagementSystem.entities.Animal;

import java.util.List;

public interface IAnimalService {

    // Add a new animal
    ResultData<AnimalResponse> addAnimal(AnimalSaveRequest request);

    // Update an existing animal
    ResultData<AnimalResponse> updateAnimal(long animalId, AnimalUpdateRequest updateRequest);

    // Retrieve all animals
    List<AnimalResponse> getAllAnimals();

    // Retrieve an animal by its ID
    AnimalResponse getAnimalById(long animalId);

    // Delete an animal by its ID
    Result deleteAnimalById(long animalId);

    // Retrieve animals by their name
    List<AnimalResponse> getAnimalsByName(String animalName);
}
