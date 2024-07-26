package dev.patika.VeterinaryManagementSystem.api;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAnimalService;
import dev.patika.VeterinaryManagementSystem.core.result.Result;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.core.utilies.Msg;
import dev.patika.VeterinaryManagementSystem.dto.request.animal.AnimalSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.animal.AnimalUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.animal.AnimalResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    // Add a new animal
    @PostMapping
    public ResultData<AnimalResponse> addAnimal(@Valid @RequestBody AnimalSaveRequest request) {
        return animalService.addAnimal(request);
    }

    // Update an existing animal
    @PutMapping("/{animalId}")
    public ResponseEntity<ResultData<AnimalResponse>> updateAnimal(
            @PathVariable Long animalId,
            @Valid @RequestBody AnimalUpdateRequest updateRequest) {

        AnimalResponse updatedAnimal = animalService.updateAnimal(animalId, updateRequest).getData();
        ResultData<AnimalResponse> result = new ResultData<>(true, Msg.ANIMAL_UPDATED, "200", updatedAnimal);

        return ResponseEntity.ok(result);
    }

    // Get all animals
    @GetMapping
    public List<AnimalResponse> getAllAnimals() {
        return animalService.getAllAnimals();
    }

    // Get an animal by ID
    @GetMapping("/{animalId}")
    public ResponseEntity<AnimalResponse> getAnimalById(@PathVariable Long animalId) {
        AnimalResponse animalResponse = animalService.getAnimalById(animalId);
        return ResponseEntity.ok(animalResponse);
    }

    // Delete an animal by ID
    @DeleteMapping("/{animalId}")
    public ResponseEntity<Result> deleteAnimalById(@PathVariable long animalId) {
        Result result = animalService.deleteAnimalById(animalId);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);  // HTTP 200 OK with result message for success
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);  // HTTP 404 Not Found with result message for failure
        }
    }

    // Search for animals by name
    @GetMapping("/search")
    public List<AnimalResponse> getAnimalsByName(@RequestParam String animalName) {
        // Call the appropriate method from IAnimalService to filter by name
        return animalService.getAnimalsByName(animalName);
    }
}
