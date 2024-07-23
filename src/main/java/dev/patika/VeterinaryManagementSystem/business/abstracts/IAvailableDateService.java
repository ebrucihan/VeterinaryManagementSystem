package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.dto.request.availabledate.AvailableDateSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.availabledate.AvailableDateResponse;

import java.time.LocalDate;
import java.util.List;

public interface IAvailableDateService {

    AvailableDateResponse addAvailableDate(AvailableDateSaveRequest request);
    List<AvailableDateResponse> getAvailableDatesByDoctor(Long doctorId);
    boolean isDoctorAvailable(Long doctorId, LocalDate date);
}
