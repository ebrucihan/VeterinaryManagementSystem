package dev.patika.VeterinaryManagementSystem.dto.response.animal;

import dev.patika.VeterinaryManagementSystem.dto.response.customer.CustomerResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalResponse {

    private long animalId;
    private String animalName;
    private String animalSpecies;
    private String animalBreed;
    private String animalGender;
    private String animalColour;
    private LocalDate animalDateOfBirth;
    private Long customerId; // Müşteri ID'si


}
