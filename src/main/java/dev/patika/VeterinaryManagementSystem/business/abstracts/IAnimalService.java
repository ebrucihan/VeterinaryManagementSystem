package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.core.result.Result;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.animal.AnimalSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.animal.AnimalUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.animal.AnimalResponse;
import dev.patika.VeterinaryManagementSystem.entities.Animal;

import java.util.List;

public interface IAnimalService {
    ResultData<AnimalResponse> addAnimal(AnimalSaveRequest request);

    ResultData<AnimalResponse> updateAnimal(long animalId, AnimalUpdateRequest updateRequest);

    List<AnimalResponse> getAllAnimals();

    AnimalResponse getAnimalById(long animalId);

    Result deleteAnimalById(long animalId);

    List<AnimalResponse> getAnimalsByName(String animalName);



}