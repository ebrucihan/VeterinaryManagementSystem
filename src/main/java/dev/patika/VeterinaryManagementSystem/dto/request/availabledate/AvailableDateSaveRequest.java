package dev.patika.VeterinaryManagementSystem.dto.request.availabledate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AvailableDateSaveRequest {


    @NotNull
    private Long doctorId;

    @NotNull
    private LocalDate date;
}
