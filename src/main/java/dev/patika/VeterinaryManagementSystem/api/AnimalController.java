package dev.patika.VeterinaryManagementSystem.api;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAnimalService;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.core.utilies.Msg;
import dev.patika.VeterinaryManagementSystem.dto.request.animal.AnimalSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.animal.AnimalUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.animal.AnimalResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/animals")
public class AnimalController {

    private final IAnimalService animalService;

    @Autowired
    public AnimalController(IAnimalService animalService) {
        this.animalService = animalService;
    }

    // Save an animal
    @PostMapping
    public ResultData<AnimalResponse> addAnimal(@Valid @RequestBody AnimalSaveRequest request) {
        return animalService.addAnimal(request);
    }

    // Update an animal
    @PutMapping("/{animalId}")
    public ResponseEntity<ResultData<AnimalResponse>> updateAnimal(
            @PathVariable Long animalId,
            @RequestBody AnimalUpdateRequest updateRequest) {

        AnimalResponse updatedAnimal = animalService.updateAnimal(animalId, updateRequest).getData();
        ResultData<AnimalResponse> result = new ResultData<>(true, Msg.ANIMAL_UPDATED, "200", updatedAnimal);

        return ResponseEntity.ok(result);
    }


    //get
    @GetMapping
    public List<AnimalResponse> getAllAnimals() {
        return animalService.getAllAnimals();
    }

    @GetMapping("/{animalId}")
    public ResponseEntity<AnimalResponse> getAnimalById(@PathVariable Long animalId) {
        AnimalResponse animalResponse = animalService.getAnimalById(animalId);
        return ResponseEntity.ok(animalResponse);
    }


    @DeleteMapping("/{animalId}")
    public ResponseEntity<Void> deleteAnimalById(@PathVariable long animalId) {
        animalService.deleteAnimalById(animalId);
        return ResponseEntity.noContent().build();
    }
}