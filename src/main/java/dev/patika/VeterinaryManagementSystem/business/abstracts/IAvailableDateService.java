package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.availabledate.AvailableDateSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.availabledate.AvailableDateUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.availabledate.AvailableDateResponse;

import java.time.LocalDate;
import java.util.List;

public interface IAvailableDateService {


    ResultData<AvailableDateResponse> addAvailableDate(AvailableDateSaveRequest request);
    ResultData<List<AvailableDateResponse>> getAvailableDatesByDoctor(Long doctorId);
    ResultData<Boolean> isDoctorAvailable(long doctorId, LocalDate date);
    ResultData<List<AvailableDateResponse>> getAllAvailableDates();
    ResultData<AvailableDateResponse> updateAvailableDate(AvailableDateUpdateRequest request);
    ResultData<String> deleteAvailableDate(Long id);
}
