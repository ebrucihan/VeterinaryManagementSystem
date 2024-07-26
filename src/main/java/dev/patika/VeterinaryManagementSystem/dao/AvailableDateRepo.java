package dev.patika.VeterinaryManagementSystem.dao;

import dev.patika.VeterinaryManagementSystem.entities.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for accessing and manipulating AvailableDate entities.
 */
@Repository
public interface AvailableDateRepo extends JpaRepository<AvailableDate, Long> {

    /**
     * Retrieves a list of AvailableDate entities for a specific doctor.
     *
     * @param doctorId The ID of the doctor to retrieve available dates for.
     * @return A list of AvailableDate entities for the specified doctor.
     */
    List<AvailableDate> findByDoctor_DoctorId(Long doctorId);

    /**
     * Checks if a specific available date exists for a given doctor.
     *
     * @param doctorId The ID of the doctor to check for.
     * @param date The date to check for.
     * @return True if an available date exists for the specified doctor on the given date, otherwise false.
     */
    boolean existsByDoctor_DoctorIdAndDate(Long doctorId, LocalDate date);

    /**
     * Finds a list of AvailableDate entities for a specific doctor on a given date.
     *
     * @param doctorId The ID of the doctor to retrieve available dates for.
     * @param date The date to retrieve available dates for.
     * @return A list of AvailableDate entities for the specified doctor on the given date.
     */
    List<AvailableDate> findByDoctor_DoctorIdAndDate(Long doctorId, LocalDate date);

}
