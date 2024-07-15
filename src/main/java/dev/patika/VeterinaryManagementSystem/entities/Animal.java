package dev.patika.VeterinaryManagementSystem.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "animals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_id")
    private long animalId;

    @Column(name = "animal_name")
    private String animalName;

    @Column(name = "animal_species")
    private String animalSpecies;

    @Column(name = "animal_breed")
    private String animalBreed;

    @Column(name = "animal_gender")
    private String animalGender;

    @Column(name = "animal_colour")
    private String animalColour;

    @Column(name = "animal_birthday")
    private LocalDate animalDateOfBirth;
}
