package dev.patika.VeterinaryManagementSystem.api;


import dev.patika.VeterinaryManagementSystem.business.abstracts.IAnimalVaccineService;
import dev.patika.VeterinaryManagementSystem.business.concretes.AnimalVaccineManager;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.animalvaccine.AnimalVaccineSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.animalvaccine.AnimalVaccineUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.animalvaccine.AnimalVaccineResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/animal-vaccines")
public class AnimalVaccineController {

    private final IAnimalVaccineService animalVaccineService;

    @Autowired
    public AnimalVaccineController(IAnimalVaccineService animalVaccineService) {
        this.animalVaccineService = animalVaccineService;
    }

    @PostMapping
    public ResponseEntity<ResultData<AnimalVaccineResponse>> addAnimalVaccine(@Valid @RequestBody AnimalVaccineSaveRequest request) {
        ResultData<AnimalVaccineResponse> result = animalVaccineService.addAnimalVaccine(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);  // HTTP 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultData<AnimalVaccineResponse>> updateAnimalVaccine(
            @PathVariable("id") Long id,
            @Valid @RequestBody AnimalVaccineUpdateRequest request) {
        ResultData<AnimalVaccineResponse> result = animalVaccineService.updateAnimalVaccine(id, request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/by-animal/{animalId}")
    public ResponseEntity<List<AnimalVaccineResponse>> getAnimalVaccinesByAnimalId(
            @PathVariable("animalId") Long animalId) {
        List<AnimalVaccineResponse> result = animalVaccineService.getAnimalVaccinesByAnimalId(animalId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/by-protection-period")
    public ResponseEntity<List<AnimalVaccineResponse>> getAnimalVaccinesByProtectionPeriod(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<AnimalVaccineResponse> result = animalVaccineService.getAnimalVaccinesByProtectionPeriod(startDate, endDate);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultData<String>> deleteAnimalVaccine(@PathVariable("id") Long id) {
        ResultData<String> result = animalVaccineService.deleteAnimalVaccine(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);  // HTTP 204 No Content
    }
}