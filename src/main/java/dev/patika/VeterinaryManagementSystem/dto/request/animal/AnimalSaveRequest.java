package dev.patika.VeterinaryManagementSystem.dto.request.animal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalSaveRequest {

    @NotBlank(message = "Animal name is mandatory")
    private String animalName;

    @NotBlank(message = "Animal species is mandatory")
    private String animalSpecies;

    @NotBlank(message = "Animal breed is mandatory")
    private String animalBreed;

    @NotBlank(message = "Animal gender is mandatory")
    private String animalGender;

    @NotBlank(message = "Animal colour is mandatory")
    private String animalColour;

    private LocalDate animalDateOfBirth; // LocalDate can be null if date of birth is unknown

    private Long customerId; // This is used to associate the animal with a customer
}
