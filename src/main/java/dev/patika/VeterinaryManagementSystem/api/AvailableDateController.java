package dev.patika.VeterinaryManagementSystem.api;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAvailableDateService;
import dev.patika.VeterinaryManagementSystem.dto.request.availabledate.AvailableDateSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.availabledate.AvailableDateResponse;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public ResponseEntity<AvailableDateResponse> addAvailableDate(@RequestBody AvailableDateSaveRequest request) {
        AvailableDateResponse response = availableDateService.addAvailableDate(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-doctor/{doctorId}")
    public ResponseEntity<List<AvailableDateResponse>> getAvailableDatesByDoctor(@PathVariable Long doctorId) {
        List<AvailableDateResponse> response = availableDateService.getAvailableDatesByDoctor(doctorId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/is-available")
    public ResponseEntity<Boolean> isDoctorAvailable(
            @RequestParam Long doctorId,
            @RequestParam LocalDate date) {
        boolean isAvailable = availableDateService.isDoctorAvailable(doctorId, date);
        return ResponseEntity.ok(isAvailable);
    }
}