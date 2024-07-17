package dev.patika.VeterinaryManagementSystem.dto.request.vaccine;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class VaccineUpdateRequest {

    @NotNull(message = "Vaccine name is required")
    private String vaccineName;

    @NotNull(message = "Vaccine code is required")
    private String vaccineCode;

    @NotNull(message = "Protection start date is required")
    private LocalDate protectionStartDate;

    @NotNull(message = "Protection finish date is required")
    private LocalDate protectionFinishDate;
}
