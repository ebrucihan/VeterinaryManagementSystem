package dev.patika.VeterinaryManagementSystem.dto.response.animalvaccine;


import dev.patika.VeterinaryManagementSystem.dto.response.animal.AnimalResponse;
import dev.patika.VeterinaryManagementSystem.dto.response.vaccine.VaccineResponse;
import dev.patika.VeterinaryManagementSystem.entities.Animal;
import dev.patika.VeterinaryManagementSystem.entities.Vaccine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalVaccineResponse {

    private Long id;
    private AnimalResponse animal;
    private VaccineResponse vaccine;
    private LocalDate applicationDate;


}
