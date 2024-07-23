package dev.patika.VeterinaryManagementSystem.dao;

import dev.patika.VeterinaryManagementSystem.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepo extends JpaRepository<Appointment,Long> {
    List<Appointment> findByDoctor_DoctorIdAndAppointmentDateTimeBetween(Long doctor, LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<Appointment> findByAnimal_AnimalIdAndAppointmentDateTimeBetween(Long animal, LocalDateTime startDateTime, LocalDateTime endDateTime);
    boolean existsByDoctor_DoctorIdAndAppointmentDateTime(Long doctorId, LocalDateTime appointmentDateTime);
    boolean existsByDoctor_DoctorIdAndAppointmentDateTimeAndAppointmentIdNot(Long doctorId, LocalDateTime appointmentDateTime, Long appointmentId);

}
