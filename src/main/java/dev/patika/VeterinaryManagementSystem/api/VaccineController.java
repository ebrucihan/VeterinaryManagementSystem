package dev.patika.VeterinaryManagementSystem.api;


import dev.patika.VeterinaryManagementSystem.business.abstracts.IVaccineService;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.vaccine.VaccineResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vaccines")
public class VaccineController {

    private final IVaccineService vaccineService;

    @Autowired
    public VaccineController(IVaccineService vaccineService) {
        this.vaccineService = vaccineService;
    }

    @PostMapping
    public ResponseEntity<ResultData<VaccineResponse>> addVaccine(@Valid @RequestBody VaccineSaveRequest request) {
        ResultData<VaccineResponse> result = vaccineService.addVaccine(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);  // HTTP 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultData<VaccineResponse>> updateVaccine(
            @PathVariable Long id,
            @Valid @RequestBody VaccineUpdateRequest request) {
        ResultData<VaccineResponse> result = vaccineService.updateVaccine(id, request);
        return ResponseEntity.ok(result);  // HTTP 200 OK
    }

    @GetMapping
    public ResponseEntity<ResultData<List<VaccineResponse>>> getAllVaccines() {
        ResultData<List<VaccineResponse>> result = vaccineService.getAllVaccines();
        return ResponseEntity.ok(result);  // HTTP 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultData<VaccineResponse>> getVaccineById(@PathVariable Long id) {
        ResultData<VaccineResponse> result = vaccineService.getVaccineById(id);
        return ResponseEntity.ok(result);  // HTTP 200 OK
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultData<String>> deleteVaccine(@PathVariable Long id) {
        ResultData<String> result = vaccineService.deleteVaccine(id);
        if (result.isStatus()) {
            return ResponseEntity.ok(result);  // HTTP 200 OK with result message
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);  // HTTP 404 Not Found with result message
        }
    }
}