package dev.patika.VeterinaryManagementSystem.api;


import dev.patika.VeterinaryManagementSystem.business.abstracts.IDoctorService;
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

    public DoctorController(IDoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<DoctorResponse> save(@RequestBody DoctorSaveRequest request) {
        DoctorResponse response = doctorService.save(request);
        response.setMessage(Msg.DOCTOR_CREATED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponse> update(@PathVariable long id, @RequestBody DoctorUpdateRequest request) {
        DoctorResponse response = doctorService.update(id, request);
        response.setMessage(Msg.DOCTOR_UPDATED);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> getById(@PathVariable long id) {
        DoctorResponse response = doctorService.getById(id);
        response.setMessage(Msg.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponse>> getAll() {
        List<DoctorResponse> responseList = doctorService.getAll();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        doctorService.delete(id);
        return new ResponseEntity<>(Msg.DOCTOR_DELETED, HttpStatus.OK);
    }
}