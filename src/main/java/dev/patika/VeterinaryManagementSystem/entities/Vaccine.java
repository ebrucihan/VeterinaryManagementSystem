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
@Table(name = "vaccines")
public class Vaccine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vaccineId; // Unique identifier for the vaccine

    @Column(nullable = false)
    private String vaccineName; // Name of the vaccine

    @Column(nullable = false)
    private String vaccineCode; // Code of the vaccine

    @Column(name = "protection_start_date", nullable = false)
    private LocalDate protectionStartDate; // Start date of the vaccine's protection period

    @Column(name = "protection_finish_date", nullable = false)
    private LocalDate protectionFinishDate; // End date of the vaccine's protection period
}


