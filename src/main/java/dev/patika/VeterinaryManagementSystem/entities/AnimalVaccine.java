package dev.patika.VeterinaryManagementSystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "animal_vaccines")
public class AnimalVaccine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for the vaccine record

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id")
    private Animal animal; // Animal that received the vaccine

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaccine_id", nullable = false)
    private Vaccine vaccine; // Vaccine administered to the animal

    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate; // Date when the vaccine was administered

}