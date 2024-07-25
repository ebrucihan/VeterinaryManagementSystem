package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.doctor.DoctorSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.doctor.DoctorUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.doctor.DoctorResponse;

import java.time.LocalDate;
import java.util.List;

public interface IDoctorService {

    ResultData<DoctorResponse> save(DoctorSaveRequest request);
    ResultData<DoctorResponse> update(long id, DoctorUpdateRequest request);
    ResultData<Void> delete(long id);
    ResultData<DoctorResponse> getById(long id);
    ResultData<List<DoctorResponse>> getAll();
    ResultData<Boolean> isDoctorAvailable(long doctorId, LocalDate date);
}
