package dev.patika.VeterinaryManagementSystem.api;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAvailableDateService;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.availabledate.AvailableDateSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.availabledate.AvailableDateUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.availabledate.AvailableDateResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/available-dates")
public class AvailableDateController {
    private final IAvailableDateService availableDateService;

    @Autowired
    public AvailableDateController(IAvailableDateService availableDateService) {
        this.availableDateService = availableDateService;
    }

    // Add a new available date for a doctor
    @PostMapping
    public ResponseEntity<ResultData<AvailableDateResponse>> addAvailableDate(
            @Valid @RequestBody AvailableDateSaveRequest request) {
        ResultData<AvailableDateResponse> result = availableDateService.addAvailableDate(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);  // HTTP 201 Created
    }

    // Update an existing available date
    @PutMapping("/{id}")
    public ResponseEntity<ResultData<AvailableDateResponse>> updateAvailableDate(
            @PathVariable Long id,
            @Valid @RequestBody AvailableDateUpdateRequest request) {
        request.setId(id);
        ResultData<AvailableDateResponse> result = availableDateService.updateAvailableDate(request);
        return ResponseEntity.ok(result);
    }

    // Get all available dates
    @GetMapping
    public ResponseEntity<ResultData<List<AvailableDateResponse>>> getAllAvailableDates() {
        ResultData<List<AvailableDateResponse>> result = availableDateService.getAllAvailableDates();
        return ResponseEntity.ok(result);
    }

    // Get available dates for a specific doctor
    @GetMapping("/by-doctor/{doctorId}")
    public ResponseEntity<ResultData<List<AvailableDateResponse>>> getAvailableDatesByDoctor(
            @PathVariable Long doctorId) {
        ResultData<List<AvailableDateResponse>> result = availableDateService.getAvailableDatesByDoctor(doctorId);
        return ResponseEntity.ok(result);
    }

    // Check if a specific doctor is available on a given date
    @GetMapping("/is-available")
    public ResponseEntity<ResultData<Boolean>> isDoctorAvailable(
            @RequestParam Long doctorId,
            @RequestParam LocalDate date) {
        ResultData<Boolean> result = availableDateService.isDoctorAvailable(doctorId, date);
        return ResponseEntity.ok(result);
    }

    // Delete an available date by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ResultData<String>> deleteAvailableDate(@PathVariable Long id) {
        ResultData<String> result = availableDateService.deleteAvailableDate(id);
        return ResponseEntity.ok(result);
    }
}
