package dev.patika.VeterinaryManagementSystem.dto.request.animalvaccine;


import dev.patika.VeterinaryManagementSystem.entities.Animal;
import dev.patika.VeterinaryManagementSystem.entities.Vaccine;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalVaccineSaveRequest {

    @NotNull(message = "Animal id is required")
    private Long animalId;

    @NotNull(message = "Vaccine id is required")
    private Long vaccineId;

    private LocalDate applicationDate;

}
