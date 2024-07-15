package dev.patika.VeterinaryManagementSystem.dto.response.animal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalResponse {

    private Long animalId;
    private String animalName;
    private String animalSpecies;
    private String animalBreed;
    private String animalGender;
    private String animalColour;
    private LocalDate animalDateOfBirth;
}
