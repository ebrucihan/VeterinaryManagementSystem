package dev.patika.VeterinaryManagementSystem.dto.request.vaccine;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineSaveRequest {

    @NotNull(message = "Vaccine name cannot be null")
    private String vaccineName;

    @NotNull(message = "Vaccine code cannot be null")
    private String vaccineCode;

    @NotNull(message = "Protection start date cannot be null")
    private LocalDate protectionStartDate;

    @NotNull(message = "Protection finish date cannot be null")
    private LocalDate protectionFinishDate;

}
