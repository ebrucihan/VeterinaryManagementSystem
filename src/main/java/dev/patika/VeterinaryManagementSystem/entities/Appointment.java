package dev.patika.VeterinaryManagementSystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long appointmentId; // Unique identifier for the appointment

    @Column(name = "appointment_date_time", nullable = false)
    private LocalDateTime appointmentDateTime; // Date and time of the appointment

    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal; // Animal that is the subject of the appointment

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor; // Doctor handling the appointment


}
