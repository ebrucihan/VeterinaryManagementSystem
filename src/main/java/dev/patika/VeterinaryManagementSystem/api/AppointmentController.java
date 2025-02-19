package dev.patika.VeterinaryManagementSystem.api;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAppointmentService;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.appointment.AppointmentUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.appointment.AppointmentResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final IAppointmentService appointmentService;

    public AppointmentController(IAppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // Create a new appointment
    @PostMapping
    public ResponseEntity<ResultData<AppointmentResponse>> createAppointment(@Valid @RequestBody AppointmentSaveRequest request) {
        ResultData<AppointmentResponse> result = appointmentService.createAppointment(request);
        if (result.isStatus()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);  // HTTP 201 Created
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);  // HTTP 409 Conflict
        }
    }

    // Get an appointment by its ID
    @GetMapping("/{id}")
    public ResponseEntity<ResultData<AppointmentResponse>> getAppointmentById(@PathVariable Long id) {
        ResultData<AppointmentResponse> result = appointmentService.getAppointmentById(id);
        if (result.isStatus()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);  // HTTP 404 Not Found
        }
    }

    // Delete an appointment by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ResultData<Void>> deleteAppointment(@PathVariable Long id) {
        ResultData<Void> result = appointmentService.deleteAppointment(id);
        if (result.isStatus()) {
            return ResponseEntity.ok(result);  // HTTP 200 OK with result message for success
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);  // HTTP 404 Not Found with result message for failure
        }
    }

    // Get a list of appointments with optional filters
    @GetMapping
    public ResponseEntity<ResultData<List<AppointmentResponse>>> getAppointments(
            @RequestParam(required = false) Long doctor,
            @RequestParam(required = false) Long animal,
            @RequestParam(required = false) LocalDateTime startDateTime,
            @RequestParam(required = false) LocalDateTime endDateTime) {
        ResultData<List<AppointmentResponse>> result = appointmentService.getAppointments(doctor, animal, startDateTime, endDateTime);
        return ResponseEntity.ok(result);
    }

    // Update an existing appointment
    @PutMapping("/{id}")
    public ResponseEntity<ResultData<AppointmentResponse>> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentUpdateRequest request) {
        // Set the appointmentId in the request body
        request.setAppointmentId(id);

        // Call the service layer to update the appointment
        ResultData<AppointmentResponse> result = appointmentService.updateAppointment(request);

        // Return response based on the result status
        if (result.isStatus()) {
            return ResponseEntity.ok(result);  // HTTP 200 OK for success
        } else {
            // Return HTTP 409 Conflict for failure cases (like doctor unavailability)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
    }

    // Get a list of appointments for a specific doctor and date range
    @GetMapping("/doctor")
    public ResponseEntity<ResultData<List<AppointmentResponse>>> getAppointmentsByDoctor(
            @RequestParam Long doctorId,
            @RequestParam LocalDateTime startDateTime,
            @RequestParam LocalDateTime endDateTime) {
        ResultData<List<AppointmentResponse>> result = appointmentService.getAppointmentsByDoctorAndDate(doctorId, startDateTime, endDateTime);
        return ResponseEntity.ok(result);
    }

    // Get a list of appointments for a specific animal and date range
    @GetMapping("/animal")
    public ResponseEntity<ResultData<List<AppointmentResponse>>> getAppointmentsByAnimal(
            @RequestParam Long animalId,
            @RequestParam LocalDateTime startDateTime,
            @RequestParam LocalDateTime endDateTime) {
        ResultData<List<AppointmentResponse>> result = appointmentService.getAppointmentsByAnimalAndDate(animalId, startDateTime, endDateTime);
        return ResponseEntity.ok(result);
    }
}
