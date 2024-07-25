package dev.patika.VeterinaryManagementSystem.dao;

import dev.patika.VeterinaryManagementSystem.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepo extends JpaRepository<Appointment,Long> {
    boolean existsByDoctor_DoctorIdAndAppointmentDateTime(long doctorId, LocalDateTime appointmentDateTime);

    boolean existsByDoctor_DoctorIdAndAppointmentDateTimeBetween(long doctorId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    boolean existsByDoctor_DoctorIdAndAppointmentDateTimeAndAppointmentIdNot(long doctorId, LocalDateTime appointmentDateTime, long appointmentId);

    List<Appointment> findByDoctor_DoctorIdAndAppointmentDateTimeAfter(long doctorId, LocalDateTime startDateTime);

    List<Appointment> findByAnimal_AnimalIdAndAppointmentDateTimeAfter(long animalId, LocalDateTime startDateTime);

    List<Appointment> findByDoctor_DoctorIdAndAppointmentDateTimeGreaterThanEqual(long doctorId, LocalDateTime startDateTime);
    List<Appointment> findByAnimal_AnimalIdAndAppointmentDateTimeGreaterThanEqual(long animalId, LocalDateTime startDateTime);

}
