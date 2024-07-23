package dev.patika.VeterinaryManagementSystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "available_dates")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "available_date_id")
    private Long id;

    @Column(name = "date",nullable = false )
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

}
