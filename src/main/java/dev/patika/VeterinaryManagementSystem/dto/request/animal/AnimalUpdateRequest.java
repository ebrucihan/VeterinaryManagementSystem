package dev.patika.VeterinaryManagementSystem.dto.request.animal;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
public class AnimalUpdateRequest {

    private String animalName;
    private String animalSpecies;
    private String animalBreed;
    private String animalGender;
    private String animalColour;
    private LocalDate animalDateOfBirth;

}
