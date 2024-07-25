package dev.patika.VeterinaryManagementSystem.dto.response.availabledate;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Data
@Getter
public class AvailableDateResponse {

    private Long id;
    private Long doctorId;
    private LocalDate date;
}
