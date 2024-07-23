package dev.patika.VeterinaryManagementSystem.dto.response.availabledate;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AvailableDateResponse {

    private Long id;
    private Long doctorId;
    private LocalDate date;
}
