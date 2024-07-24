package dev.patika.VeterinaryManagementSystem.dto.request.animalvaccine;

import dev.patika.VeterinaryManagementSystem.entities.Vaccine;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AnimalVaccineUpdateRequest {

    @NotNull(message = "Vaccine ID is required")
    private Long vaccineId;

    @NotNull(message = "Application date is required")
    private LocalDate applicationDate;
}
