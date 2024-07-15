package dev.patika.VeterinaryManagementSystem.dto.request.animal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalSaveRequest {

    @NotNull
    private String animalName;

    @NotNull
    private String animalSpecies;

    @NotNull
    private String animalBreed;

    @NotNull
    private String animalGender;

    @NotNull
    private String animalColour;

    @NotNull
    private LocalDate animalDateOfBirth;

   @NotNull
    private Long customerId;
}
