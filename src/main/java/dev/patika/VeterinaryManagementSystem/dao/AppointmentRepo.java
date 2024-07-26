package dev.patika.VeterinaryManagementSystem.dao;

import dev.patika.VeterinaryManagementSystem.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for accessing and manipulating Appointment entities.
 */
@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

    /**
     * Checks if an appointment exists for a specific doctor at a given date and time.
     *
     * @param doctorId The ID of the doctor to check for.
     * @param appointmentDateTime The date and time of the appointment to check for.
     * @return True if an appointment exists for the specified doctor at the given date and time, otherwise false.
     */
    boolean existsByDoctor_DoctorIdAndAppointmentDateTime(long doctorId, LocalDateTime appointmentDateTime);

    /**
     * Checks if an appointment exists for a specific doctor within a given date and time range.
     *
     * @param doctorId The ID of the doctor to check for.
     * @param startDateTime The start of the date and time range to check within.
     * @param endDateTime The end of the date and time range to check within.
     * @return True if an appointment exists for the specified doctor within the given date and time range, otherwise false.
     */
    boolean existsByDoctor_DoctorIdAndAppointmentDateTimeBetween(long doctorId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    /**
     * Checks if an appointment exists for a specific doctor at a given date and time, excluding a specific appointment ID.
     *
     * @param doctorId The ID of the doctor to check for.
     * @param appointmentDateTime The date and time of the appointment to check for.
     * @param appointmentId The ID of the appointment to exclude from the check.
     * @return True if an appointment exists for the specified doctor at the given date and time, excluding the specified appointment ID, otherwise false.
     */
    boolean existsByDoctor_DoctorIdAndAppointmentDateTimeAndAppointmentIdNot(long doctorId, LocalDateTime appointmentDateTime, long appointmentId);

    /**
     * Finds a list of appointments for a specific doctor within a given date and time range.
     *
     * @param doctorId The ID of the doctor to retrieve appointments for.
     * @param startDateTime The start of the date and time range to search within.
     * @param endDateTime The end of the date and time range to search within.
     * @return A list of appointments for the specified doctor within the given date and time range.
     */
    List<Appointment> findByDoctor_DoctorIdAndAppointmentDateTimeBetween(long doctorId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    /**
     * Finds a list of appointments for a specific animal within a given date and time range.
     *
     * @param animalId The ID of the animal to retrieve appointments for.
     * @param startDateTime The start of the date and time range to search within.
     * @param endDateTime The end of the date and time range to search within.
     * @return A list of appointments for the specified animal within the given date and time range.
     */
    List<Appointment> findByAnimal_AnimalIdAndAppointmentDateTimeBetween(long animalId, LocalDateTime startDateTime, LocalDateTime endDateTime);

}
