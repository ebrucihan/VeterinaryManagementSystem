package dev.patika.VeterinaryManagementSystem.api;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IDoctorService;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.core.utilies.Msg;
import dev.patika.VeterinaryManagementSystem.dto.request.doctor.DoctorSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.doctor.DoctorUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.doctor.DoctorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final IDoctorService doctorService;

    @Autowired
    public DoctorController(IDoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // Save a new doctor
    @PostMapping
    public ResponseEntity<DoctorResponse> save(@RequestBody DoctorSaveRequest request) {
        ResultData<DoctorResponse> resultData = doctorService.save(request);
        DoctorResponse response = resultData.getData();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response); // Created message is handled in ResultData
    }

    // Update an existing doctor
    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponse> update(
            @PathVariable long id,
            @RequestBody DoctorUpdateRequest request) {
        ResultData<DoctorResponse> resultData = doctorService.update(id, request);
        DoctorResponse response = resultData.getData();
        return ResponseEntity.status(HttpStatus.OK)
                .body(response); // Updated message is handled in ResultData
    }

    // Get a doctor by ID
    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> getById(@PathVariable long id) {
        ResultData<DoctorResponse> resultData = doctorService.getById(id);
        DoctorResponse response = resultData.getData();
        return ResponseEntity.status(HttpStatus.OK)
                .body(response); // OK message is handled in ResultData
    }

    // Get all doctors
    @GetMapping
    public ResponseEntity<List<DoctorResponse>> getAll() {
        ResultData<List<DoctorResponse>> resultData = doctorService.getAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(resultData.getData()); // OK message is handled in ResultData
    }

    // Delete a doctor by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        ResultData<Void> resultData = doctorService.delete(id);
        String message = resultData.getMessage() != null ? resultData.getMessage() : Msg.DOCTOR_DELETED;
        return ResponseEntity.status(HttpStatus.OK)
                .body(message); // Message from ResultData or default message from Msg class
    }
}
