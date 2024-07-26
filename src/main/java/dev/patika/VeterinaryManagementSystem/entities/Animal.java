package dev.patika.VeterinaryManagementSystem.entities;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "animals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_id")
    private long animalId; // Unique identifier for the animal

    @Column(name = "animal_name")
    private String animalName; // Name of the animal

    @Column(name = "animal_species")
    private String animalSpecies; // Species of the animal

    @Column(name = "animal_breed")
    private String animalBreed; // Breed of the animal

    @Column(name = "animal_gender")
    private String animalGender; // Gender of the animal

    @Column(name = "animal_colour")
    private String animalColour; // Color of the animal

    @Column(name = "animal_birthday")
    private LocalDate animalDateOfBirth; // Birthdate of the animal

    // Many animals can belong to one customer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer; // Customer to whom the animal belongs

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnimalVaccine> vaccines = new ArrayList<>(); // List of vaccines for the animal
}