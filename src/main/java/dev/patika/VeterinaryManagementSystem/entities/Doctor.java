package dev.patika.VeterinaryManagementSystem.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "doctors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Long doctorId; // Unique identifier for the doctor

    @Column(name = "doctor_name")
    private String doctorName; // Name of the doctor

    @Column(name = "doctor_phone")
    private String doctorPhone; // Phone number of the doctor

    @Column(name = "doctor_mail")
    private String doctorMail; // Email address of the doctor

    @Column(name = "doctor_address")
    private String doctorAddress; // Address of the doctor

    @Column(name = "doctor_city")
    private String doctorCity; // City where the doctor practices

    @OneToMany(mappedBy = "doctor")
    private List<AvailableDate> availableDates; // List of available dates for the doctor
}
