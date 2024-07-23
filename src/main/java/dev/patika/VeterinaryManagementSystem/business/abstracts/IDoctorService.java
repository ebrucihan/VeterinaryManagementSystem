package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.dto.request.doctor.DoctorSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.doctor.DoctorUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.doctor.DoctorResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IDoctorService {

    DoctorResponse save(DoctorSaveRequest request);
    DoctorResponse update(long id, DoctorUpdateRequest request);
    void delete(long id);
    DoctorResponse getById(long id);
    List<DoctorResponse> getAll();
    boolean isDoctorAvailable(long doctor, LocalDate date);
}
